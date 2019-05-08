package com.graduation.hp.repository.model.impl;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.vo.ArticleVO;
import com.graduation.hp.repository.http.service.ArticleService;
import com.graduation.hp.repository.model.IArticleModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

public class ArticleModel extends BaseModel
        implements IArticleModel {

    private RepositoryHelper mRepositoryHelper;

    @Inject
    public ArticleModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

    @Override
    public Single<List<ArticleVO>> getArticleListByTypeId(long typeId, int offset, int limit) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            Map<String, Object> map = new HashMap<>();
            map.put(Key.TYPE_ID, typeId);
            map.put(Key.LIMIT, limit);
            map.put(Key.OFFSET, offset);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(ArticleService.class)
                .dataGrid(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToList(ArticleVO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<List<ArticleVO>> getArticleListByUserId(long userId, int offset, int limit) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            Map<String, Object> map = new HashMap<>();
            map.put(Key.USER_ID_CAMEL_CASE, userId);
            map.put(Key.LIMIT, limit);
            map.put(Key.OFFSET, offset);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(ArticleService.class)
                .dataGrid(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToList(ArticleVO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<ArticleVO> getNewsById(long newsId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            if (newsId == 0) {
                throw new ApiException(ResponseCode.ILLEGAL_ARGUMENT);
            }
            Map<String, Object> map = new HashMap<>();
            map.put(Key.ID, newsId);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(ArticleService.class)
                .queryByCondition(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToData(ArticleVO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    public RepositoryHelper getRepositoryHelper() {
        return mRepositoryHelper;
    }
}
