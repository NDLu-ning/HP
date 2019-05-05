package com.graduation.hp.repository.model.impl;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.event.UploadProfileEvent;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.utils.RetrofitUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.service.UploadService;
import com.graduation.hp.repository.model.IUploadModel;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Single;

public class UploadModel extends BaseModel
        implements IUploadModel {

    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public UploadModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

    @Override
    public Single<UploadProfileEvent> uploadFile(File file) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return httpHelper.obtainRetrofitService(UploadService.class)
                .uploadFile(RetrofitUtils.createMultipart(Key.FILE, file))
                .compose(RxUtils.transformResultToData(String.class))
                .map(url -> new UploadProfileEvent(url))
                .compose(RxUtils.rxSchedulerHelper());
    }
}
