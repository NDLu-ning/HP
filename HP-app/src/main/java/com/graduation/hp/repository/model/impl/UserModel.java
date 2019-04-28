package com.graduation.hp.repository.model.impl;

import android.text.TextUtils;

import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.repository.model.IUserModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class UserModel extends BaseModel implements IUserModel {

    private RepositoryHelper mRepositoryHelper;

    @Inject
    public UserModel(RepositoryHelper mRepositoryHelper) {
        this.mRepositoryHelper = mRepositoryHelper;
    }

    @Override
    public Single<String> sendVerificationMessage(String phone) {
        return null;
    }

    @Override
    public Single<Result> login(String username, String password) {
        return Single.create(emitter -> {
            boolean usernamePassed = !TextUtils.isEmpty(username) && username.length() >= 6;
            boolean passwordPassed = !TextUtils.isEmpty(password) && password.length() >= 6;
            if(!usernamePassed) {
//                emitter.onError(new ApiException(ResponseCode));
            }
        });
    }

    @Override
    public Single<Result> saveCurrentUserInfo(User user) {
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.create((SingleOnSubscribe<Result>) emitter -> {
            preferencesHelper.saveCurrentUserInfo(user);
            emitter.onSuccess(Result.ok());
        }).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Single<Result> signup() {
        return null;
    }

    @Override
    public Single<Boolean> isCurrentUserLogin() {
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.just(!TextUtils.isEmpty(preferencesHelper.getCurrentUserToken()));
    }

    @Override
    public Single<Result> clearUserInfo() {
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.create(emitter -> {
            preferencesHelper.clearUserInfo();
            emitter.onSuccess(Result.ok());
        });
    }

    public RepositoryHelper getRepositoryHelper() {
        return mRepositoryHelper;
    }
}
