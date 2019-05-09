package com.graduation.hp.repository.model.impl;

import android.text.TextUtils;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.vo.InvitationVO;
import com.graduation.hp.repository.http.service.InvitationService;
import com.graduation.hp.repository.model.IInvitationModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;
import com.graduation.hp.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

public class InvitationModel extends BaseModel
        implements IInvitationModel {

    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public InvitationModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

    @Override
    public Single<List<InvitationVO>> getInvitationList(int offset, int limit) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            Map<String, Object> map = new HashMap<>();
            map.put(Key.LIMIT, limit);
            map.put(Key.OFFSET, offset);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(InvitationService.class)
                .dataGrid(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToList(InvitationVO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<List<InvitationVO>> getInvitationListByPhysiqueId(long physiqueId, int offset, int limit) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            Map<String, Object> map = new HashMap<>();
            map.put(Key.PHYSIQUELD, physiqueId);
            map.put(Key.LIMIT, limit);
            map.put(Key.OFFSET, offset);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(InvitationService.class)
                .dataGrid(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToList(InvitationVO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<List<InvitationVO>> getInvitationListByUserId(long userId, int offset, int limit) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            Map<String, Object> map = new HashMap<>();
            map.put(Key.USER_ID_CAMEL_CASE, userId);
            map.put(Key.LIMIT, limit);
            map.put(Key.OFFSET, offset);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(InvitationService.class)
                .dataGrid(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToList(InvitationVO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<InvitationVO> getInvitationById(long invitationId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            Map<String, Object> map = new HashMap<>();
            map.put(Key.ID, invitationId);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(InvitationService.class)
                .queryByCondition(JsonUtils.mapToRequestBody(params))
                .compose(RxUtils.transformResultToData(InvitationVO.class))
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<Boolean> createInvitation(String title, String content, List<String> pics) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        LogUtils.d(mRepositoryHelper.getPreferencesHelper().getCurrentUserToken());

        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            long userId = preferencesHelper.getCurrentUserId();
            if (userId == 0L) throw new ApiException(ResponseCode.TOKEN_ERROR);
            Map<String, Object> map = new HashMap<>();
            map.put(Key.USER_ID_CAMEL_CASE, userId);
            map.put(Key.TITLE, title);
            map.put(Key.CONTEXT, content);
            for (int i = 0; i < pics.size(); i++) {
                String url = pics.get(i);
                if (!TextUtils.isEmpty(url)) {
                    map.put(String.format(Key.PIC, i > 0 ? i + 1 + "" : ""), url);
                }
            }
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(InvitationService.class)
                .saveData(JsonUtils.mapToRequestBody(params))
                .map(RxUtils.mappingResponseToResultWithNoException(String.class))
                .compose(RxUtils.mappingResultToCheck())
                .compose(RxUtils.rxSchedulerHelper()));
    }


    public RepositoryHelper getRepositoryHelper() {
        return mRepositoryHelper;
    }
}
