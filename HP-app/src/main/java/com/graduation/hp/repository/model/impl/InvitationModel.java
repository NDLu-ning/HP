package com.graduation.hp.repository.model.impl;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.vo.InvitationVO;
import com.graduation.hp.repository.http.service.InvitationService;
import com.graduation.hp.repository.model.IInvitationModel;

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
    public Single<List<InvitationVO>> getInvitationListByTypeId(long typeId, int offset, int limit) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            Map<String, Object> map = new HashMap<>();
            map.put(Key.TYPE_ID, typeId);
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

    public RepositoryHelper getRepositoryHelper() {
        return mRepositoryHelper;
    }
}
