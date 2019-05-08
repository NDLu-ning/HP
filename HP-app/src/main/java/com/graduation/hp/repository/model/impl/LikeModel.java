package com.graduation.hp.repository.model.impl;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.service.LikeService;
import com.graduation.hp.repository.model.ILikeModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Function;

public class LikeModel extends BaseModel
        implements ILikeModel {

    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public LikeModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

    @Override
    public Single<Boolean> like(long articleId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            if (articleId == 0L) {
                emitter.onError(new ApiException(ResponseCode.ILLEGAL_ARGUMENT));
            }
            Map<String, Object> params = new HashMap<>();
            params.put(Key.ARTICLE_ID, articleId);
            emitter.onSuccess(params);
        }).flatMap(params -> httpHelper.obtainRetrofitService(LikeService.class)
                .like(JsonUtils.mapToRequestBody(params))
                .map(RxUtils.mappingResponseToResultWithNoException(Result.class))
                .map(checkLikeStatus())
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<Boolean> likeInvitation(long invitationId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            if (invitationId == 0L) {
                emitter.onError(new ApiException(ResponseCode.ILLEGAL_ARGUMENT));
            }
            Map<String, Object> params = new HashMap<>();
            params.put(Key.ARTICLE_ID, invitationId);
            emitter.onSuccess(params);
        }).flatMap(params -> httpHelper.obtainRetrofitService(LikeService.class)
                .like(JsonUtils.mapToRequestBody(params))
                .map(RxUtils.mappingResponseToResultWithNoException(Result.class))
                .map(checkLikeStatus())
                .compose(RxUtils.rxSchedulerHelper()));
    }

    private Function<Result, Boolean> checkLikeStatus() {
        return result -> {
            if (result.getStatus() == ResponseCode.FOCUS_ON.getStatus()) {
                return true;
            } else if (result.getStatus() == ResponseCode.CANCEL_FOCUS_ON.getStatus()) {
                return false;
            }
            throw new ApiException(result.getStatus(), result.getMsg());
        };
    }
}
