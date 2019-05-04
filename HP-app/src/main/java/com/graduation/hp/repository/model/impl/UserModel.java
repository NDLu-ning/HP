package com.graduation.hp.repository.model.impl;

import android.text.TextUtils;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.repository.http.entity.local.UserVO;
import com.graduation.hp.repository.http.service.AttentionService;
import com.graduation.hp.repository.http.service.UserService;
import com.graduation.hp.repository.model.IUserModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;
import com.graduation.hp.core.utils.VerifyUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

public class UserModel extends BaseModel
        implements IUserModel {

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
    public Single<User> getCurrentInfo() {
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.create((SingleOnSubscribe<User>) emitter -> {
            if (!TextUtils.isEmpty(preferencesHelper.getCurrentUserToken())) {
                User user = new User();
                user.setId(preferencesHelper.getCurrentUserId());
                user.setHeadUrl(preferencesHelper.getCurrentUserIcon());
                user.setRemark(preferencesHelper.getCurrentUserRemark());
                user.setUsername(preferencesHelper.getCurrentUserUsername());
                user.setNickname(preferencesHelper.getCurrentUserNickname());
                emitter.onSuccess(user);
            }else {
                emitter.onError(new ApiException(ResponseCode.TOKEN_ERROR));
            }
        });
    }

    @Override
    public Single<UserVO> getUserInfo(long userId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
//        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
//            Map<String, Object> map = new HashMap<>();
//            map.put(Key.ID, userId);
//        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)
//                .getUserInfo(JsonUtils.mapToRequestBody(result))
//                .compose(RxUtils.transformResultToData(User.class))
//                .subscribeOn(Schedulers.io())
//        ).flatMap(result -> httpHelper.obtainRetrofitService(AttentionService.class)
//                .countAttentionNumber(JsonUtils.mapToRequestBody3(new String[]{"authorId"}, new Long[]{userId}))
//                .compose(RxUtils.transformResultToData(Long.class))
//                .map(attentionNumber -> new UserVO(result, attentionNumber))
//                .compose(RxUtils.rxSchedulerHelper())
//        );
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.create((SingleOnSubscribe<User>) emitter -> {
            User user = new User();
            user.setId(preferencesHelper.getCurrentUserId());
            user.setHeadUrl(preferencesHelper.getCurrentUserIcon());
            user.setRemark("越过山丘，才发现无人等候；喋喋不休，再也唤不回温柔。为何记不得上一次是谁给的拥抱，在什么时候~~~");
            user.setUsername(preferencesHelper.getCurrentUserUsername());
            user.setNickname(preferencesHelper.getCurrentUserNickname());
            emitter.onSuccess(user);
        }).flatMap(result -> httpHelper.obtainRetrofitService(AttentionService.class)
                .countAttentionNumber(JsonUtils.mapToRequestBody3(new String[]{"authorId"}, new Long[]{userId}))
                .compose(RxUtils.transformResultToData(Long.class))
                .map(attentionNumber -> new UserVO(result, attentionNumber))
                .compose(RxUtils.rxSchedulerHelper())
        );
    }

    @Override
    public Single<User> login(String username, String password) { // 登录验证数据
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.create((SingleOnSubscribe<Map<String, String>>) emitter -> {
            boolean usernamePassed = VerifyUtils.isLengthVerified(username, 1, 16);
            boolean passwordPassed = VerifyUtils.isLengthVerified(password, 1, 16);
            if (!usernamePassed) {
                emitter.onError(new ApiException(ResponseCode.INPUT_USERNAME_ERROR));
            }
            if (!passwordPassed) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PASSWORD_ERROR));
            }
            Map<String, String> map = new HashMap<>();
            map.put(Key.USERNAME, username);
            map.put(Key.PASSWORD, password);
            emitter.onSuccess(map);
        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)// 登录
                .login(JsonUtils.mapToRequestBody2(result))
                .compose(RxUtils.transformResultToData(User.class))
                .doOnSuccess(preferencesHelper::saveCurrentUserInfo)
        ).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Single<Boolean> signup(String username, String password, String repassword, String phone) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, String>>) emitter -> {
            if (!VerifyUtils.isLengthVerified(username, 6, 16)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_USERNAME_ERROR));
            }
            if (!VerifyUtils.isLengthVerified(password, 6, 16)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PASSWORD_ERROR));
            }
            if (!VerifyUtils.isLengthVerified(repassword, 6, 16)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_REPASSWORD_ERROR));
            }
            if (!(!TextUtils.isEmpty(password) && password.equals(repassword))) {
                emitter.onError(new ApiException(ResponseCode.NOT_SAME_ERROR));
            }
            if (!VerifyUtils.isPhoneVerified(phone)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PHONENUMBER_ERROR));
            }
            Map<String, String> params = new HashMap<>();
            params.put(Key.USERNAME, username);
            params.put(Key.PASSWORD, password);
            params.put("phone", phone);
            emitter.onSuccess(params);
        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)
                .singup(JsonUtils.mapToRequestBody2(result))
                .map(RxUtils.mappingResponseToResult(Boolean.class))
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.mappingResultToCheck()));
    }

    @Override
    public Single<Boolean> updatePassword(String phoneNumber, String password, String repassword) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {

        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)
                .updatePassword(JsonUtils.mapToRequestBody(result))
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
