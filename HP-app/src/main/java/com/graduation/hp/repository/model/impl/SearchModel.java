package com.graduation.hp.repository.model.impl;

import android.text.TextUtils;

import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.local.SearchKeyword;
import com.graduation.hp.repository.model.ISearchModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

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
}
