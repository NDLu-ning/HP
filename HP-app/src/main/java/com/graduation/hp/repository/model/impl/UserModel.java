package com.graduation.hp.repository.model.impl;

import android.text.TextUtils;

import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.repository.http.service.UserService;
import com.graduation.hp.repository.model.IUserModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;
import com.graduation.hp.utils.VerifyUtils;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

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
    public Single<User> login(String username, String password) { // 登录验证数据
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            boolean usernamePassed = VerifyUtils.isLengthVerified(username, 6, 16);
            boolean passwordPassed = VerifyUtils.isLengthVerified(password, 6, 16);
            if (!usernamePassed) {
                emitter.onError(new ApiException(ResponseCode.INPUT_USERNAME_ERROR));
            }
            if (!passwordPassed) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PASSWORD_ERROR));
            }
            emitter.onSuccess(Boolean.TRUE);
        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)// 登录
                .login(username, password)
                .compose(RxUtils.transformResultToData(String.class))
                .doOnSuccess(preferencesHelper::updateCurrentUserToken)
        ).flatMap(isLoginSuccess -> Single.just(new User())
//                httpHelper.obtainRetrofitService(UserService.class)// 请求用户数据
//                .getUserInfo()
//                .compose(RxUtils.transformResultToData(User.class))
//                .compose(RxUtils.rxSchedulerHelper())
//                .doOnSuccess(preferencesHelper::saveCurrentUserInfo)
        ) .compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Single<Boolean> signup(String username, String password, String repassword, String phone) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            if(!VerifyUtils.isLengthVerified(username, 6, 16)){
                emitter.onError(new ApiException(ResponseCode.INPUT_USERNAME_ERROR));
            }
            if(!VerifyUtils.isLengthVerified(password, 6, 16)){
                emitter.onError(new ApiException(ResponseCode.INPUT_PASSWORD_ERROR));
            }
            if(!VerifyUtils.isLengthVerified(repassword, 6, 16)){
                emitter.onError(new ApiException(ResponseCode.INPUT_REPASSWORD_ERROR));
            }
            if(!(!TextUtils.isEmpty(password) && password.equals(repassword))){
                emitter.onError(new ApiException(ResponseCode.NOT_SAME_ERROR));
            }
            if(!VerifyUtils.isPhoneVerified(phone)){
                emitter.onError(new ApiException(ResponseCode.INPUT_PHONENUMBER_ERROR));
            }
        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)
                .singup()
                .map(RxUtils.mappingResponseToResult(Boolean.class))
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.mappingResultToCheck()));
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
