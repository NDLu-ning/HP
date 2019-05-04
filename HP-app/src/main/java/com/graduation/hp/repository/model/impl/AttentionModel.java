package com.graduation.hp.repository.model.impl;

import android.text.TextUtils;

import com.graduation.hp.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.FocusPO;
import com.graduation.hp.repository.http.service.AttentionService;
import com.graduation.hp.repository.model.IAttentionModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Function;

public class AttentionModel extends BaseModel
        implements IAttentionModel {


    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public AttentionModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

    @Override
    public Single<Boolean> focusUser(long authorId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            if (authorId == -1) {
                emitter.onError(new ApiException(ResponseCode.ILLEGAL_ARGUMENT));
            }
            Map<String, Object> map = new HashMap<>();
            map.put(Key.AUTHOR_ID, authorId);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(AttentionService.class)
                .focusUser(JsonUtils.mapToRequestBody(params))
                .map(RxUtils.mappingResponseToResultWithNoException(Object.class))
                .map(checkAttentionStatus())
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<Long> getAttentionNumber(long userId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            if (userId == -1) {
                emitter.onError(new ApiException(ResponseCode.ILLEGAL_ARGUMENT));
            }
            Map<String, Object> map = new HashMap<>();
            map.put(Key.AUTHOR_ID, userId);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(AttentionService.class)
                .countAttentionNumber(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToData(Long.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<List<FocusPO>> getAttentionList(int offset, int limit) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            Map<String, Object> map = new HashMap<>();
            map.put(Key.LIMIT, limit);
            map.put(Key.OFFSET, offset);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(AttentionService.class)
                .focusDataGrid(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToList(FocusPO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<Boolean> isFocusOn(long authorId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            if (authorId == 0L) {
                throw new ApiException(ResponseCode.ILLEGAL_ARGUMENT);
            }
            Map<String, Object> map = new HashMap<>();
            map.put(Key.AUTHOR_ID, authorId);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(AttentionService.class)
                .isFocus(JsonUtils.mapToRequestBody(params))
                .map(RxUtils.mappingResponseToResultWithNoException(Result.class))
                .map(checkAttentionStatus())
                .compose(RxUtils.rxSchedulerHelper()));
    }

    private Function<Result, Boolean> checkAttentionStatus() {
        return result -> {
            if (result.getStatus() == ResponseCode.FOCUS_ON.getStatus()) {
                return true;
            } else if (result.getStatus() == ResponseCode.UN_FOCUS_ON.getStatus()) {
                return false;
            }
            throw new ApiException(result.getStatus(), result.getMsg());
        };
    }
}
