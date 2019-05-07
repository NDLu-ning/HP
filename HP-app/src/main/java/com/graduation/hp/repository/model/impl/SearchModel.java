package com.graduation.hp.repository.model.impl;

import android.text.TextUtils;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.vo.ArticleVO;
import com.graduation.hp.repository.http.entity.pojo.SearchKeyword;
import com.graduation.hp.repository.http.service.ArticleService;
import com.graduation.hp.repository.model.ISearchModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

public class SearchModel extends BaseModel
        implements ISearchModel {

    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public SearchModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

    @Override
    public Single<Result> saveSearchKeyword(String keyword) {
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.<String>create(emitter -> {
            String source = preferencesHelper.getSearchKeywordsJson();
            ArrayList<SearchKeyword> list = (ArrayList<SearchKeyword>) JsonUtils.jsonToList(source, SearchKeyword.class);
            if (list == null) {
                list = new ArrayList<>();
            }
            boolean search = false;
            if (list.size() > 0) {
                for (SearchKeyword key : list) {
                    if (keyword.equals(key.getKeyword())) {
                        key.setQueryCount(key.getQueryCount() + 1);
                        search = true;
                        break;
                    }
                }
            }
            if (!search) {
                list.add(new SearchKeyword(keyword));
            }
            Collections.sort(list);
            emitter.onSuccess(JsonUtils.objectToJson(list));
        }).map(json -> {
            preferencesHelper.saveSearchKeywordsJson(json);
            return Result.ok();
        });
    }

    @Override
    public Single<List<SearchKeyword>> getSearchKeywords() {
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.just(preferencesHelper.getSearchKeywordsJson())
                .<List<SearchKeyword>>flatMap(json -> {
                    ArrayList<SearchKeyword> list = new ArrayList<>();
                    if (!TextUtils.isEmpty(json)) {
                        list.addAll(JsonUtils.jsonToList(json, SearchKeyword.class));
                    }
                    return Single.just(list);
                })
                .compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Single<List<ArticleVO>> searchKeywords(String keyword, int offset, int limit) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            Map<String, Object> map = new HashMap<>();
            map.put(Key.TITLE, keyword);
            map.put(Key.LIMIT, limit);
            map.put(Key.OFFSET, offset);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(ArticleService.class)
                .dataGrid(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToList(ArticleVO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }
}
