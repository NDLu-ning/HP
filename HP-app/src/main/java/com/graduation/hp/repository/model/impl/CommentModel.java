package com.graduation.hp.repository.model.impl;

import android.text.TextUtils;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.model.ICommentModel;
import com.graduation.hp.repository.http.entity.pojo.ArticleDiscussPO;
import com.graduation.hp.repository.http.service.DiscussService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

public class CommentModel extends BaseModel
        implements ICommentModel {

    public static final int DISCUSS_COMMENT = 1;
    public static final int DISCUSS_REPLY = 2;

    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public CommentModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }


    @Override
    public Single<Boolean> discuss(long articleId, String content, int discussType, long talkerUserId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            if (articleId == 0L || TextUtils.isEmpty(content)) {
                emitter.onError(new ApiException(ResponseCode.ILLEGAL_ARGUMENT));
            }
            Map<String, Object> params = new HashMap<>();
            params.put(Key.ARTICLE_ID, articleId);
            params.put(Key.CONTEXT, content);
            params.put(Key.DISCUSS_TYPE, discussType);
            if (discussType == DISCUSS_REPLY) {
                params.put(Key.TALKER_USER_ID, talkerUserId);
            }
            emitter.onSuccess(params);
        }).flatMap(params -> httpHelper.obtainRetrofitService(DiscussService.class)
                .discuss(JsonUtils.mapToRequestBody(params))
                .map(RxUtils.mappingResponseToResult(Object.class))
                .compose(RxUtils.mappingResultToCheck())
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<List<ArticleDiscussPO>> getDiscuss(long articleId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.<Map<String, Object>>create(emitter -> {
            if (articleId == 0L) {
                emitter.onError(new ApiException(ResponseCode.ILLEGAL_ARGUMENT));
            }
            Map<String, Object> params = new HashMap<>();
            params.put(Key.ARTICLE_ID, articleId);
            emitter.onSuccess(params);
        }).flatMap(params -> httpHelper.obtainRetrofitService(DiscussService.class)
                .getDiscuss(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToList(ArticleDiscussPO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }
}
