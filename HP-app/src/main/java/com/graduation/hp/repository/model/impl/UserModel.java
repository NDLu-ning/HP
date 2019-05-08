package com.graduation.hp.repository.model.impl;

import android.text.TextUtils;

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
import com.graduation.hp.repository.http.entity.vo.UserVO;
import com.graduation.hp.repository.http.entity.wrapper.UserVOWrapper;
import com.graduation.hp.repository.http.service.AttentionService;
import com.graduation.hp.repository.http.service.UserService;
import com.graduation.hp.repository.model.IUserModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;
import com.graduation.hp.core.utils.VerifyUtils;

import org.w3c.dom.Text;

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
    public Single<Integer> sendVerificationMessage(String phone) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, String>>) emitter -> {
            boolean usernamePassed = VerifyUtils.isPhoneVerified(phone);
            if (!usernamePassed) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PHONE_NUMBER_ERROR));
            }
            Map<String, String> map = new HashMap<>();
            map.put(Key.USERNAME, phone);
            emitter.onSuccess(map);
        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)// 登录
                .sms(JsonUtils.mapToRequestBody2(result))
                .compose(RxUtils.transformResultToData(Integer.class))
        ).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Single<UserVO> getCurrentInfo() {
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.create((SingleOnSubscribe<UserVO>) emitter -> {
            if (!TextUtils.isEmpty(preferencesHelper.getCurrentUserToken())) {
                UserVO user = new UserVO();
                user.setId(preferencesHelper.getCurrentUserId());
                user.setHeadUrl(preferencesHelper.getCurrentUserIcon());
                user.setRemark(preferencesHelper.getCurrentUserRemark());
                user.setUsername(preferencesHelper.getCurrentUserUsername());
                user.setNickname(preferencesHelper.getCurrentUserNickname());
                user.setSex(preferencesHelper.getCurrentUserGender());
                user.setPhysiquId(preferencesHelper.getCurrentUserPhysiquId());
                emitter.onSuccess(user);
            } else {
                emitter.onError(new ApiException(ResponseCode.TOKEN_ERROR));
            }
        });
    }

    @Override
    public Single<UserVOWrapper> getUserInfo(long userId) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
//        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
//            Map<String, Object> map = new HashMap<>();
//            map.put(Key.ID, userId);
//        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)
//                .getUserInfo(JsonUtils.mapToRequestBody(result))
//                .compose(RxUtils.transformResultToData(UserVO.class))
//                .subscribeOn(Schedulers.io())
//        ).flatMap(result -> httpHelper.obtainRetrofitService(AttentionService.class)
//                .countAttentionNumber(JsonUtils.mapToRequestBody3(new String[]{"authorId"}, new Long[]{userId}))
//                .compose(RxUtils.transformResultToData(Long.class))
//                .map(attentionNumber -> new UserVOWrapper(result, attentionNumber))
//                .compose(RxUtils.rxSchedulerHelper())
//        );
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.create((SingleOnSubscribe<UserVO>) emitter -> {
            UserVO user = new UserVO();
            user.setId(preferencesHelper.getCurrentUserId());
            user.setSex(preferencesHelper.getCurrentUserGender());
            user.setRemark(preferencesHelper.getCurrentUserRemark());
            user.setPhysiquId(preferencesHelper.getCurrentUserPhysiquId());
            user.setHeadUrl(preferencesHelper.getCurrentUserIcon());
            user.setUsername(preferencesHelper.getCurrentUserUsername());
            user.setNickname(preferencesHelper.getCurrentUserNickname());
            emitter.onSuccess(user);
        }).flatMap(result -> httpHelper.obtainRetrofitService(AttentionService.class)
                .countAttentionNumber(JsonUtils.mapToRequestBody3(new String[]{"authorId"}, new Long[]{userId}))
                .compose(RxUtils.transformResultToData(Long.class))
                .map(attentionNumber -> new UserVOWrapper(result, attentionNumber))
        ).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Single<UserVO> login(String username, String password) { // 登录验证数据
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.create((SingleOnSubscribe<Map<String, String>>) emitter -> {
            boolean usernamePassed = VerifyUtils.isLengthVerified(username, 1, 16);
            boolean passwordPassed = VerifyUtils.isLengthVerified(password, 1, 16);
            if (!usernamePassed) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PHONE_NUMBER_ERROR));
            } else if (!passwordPassed) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PASSWORD_ERROR));
            }
            Map<String, String> map = new HashMap<>();
            map.put(Key.USERNAME, username);
            map.put(Key.PASSWORD, password);
            emitter.onSuccess(map);
        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)// 登录
                .login(JsonUtils.mapToRequestBody2(result))
                .compose(RxUtils.transformResultToData(UserVO.class))
                .doOnSuccess(preferencesHelper::saveCurrentUserInfo)
        ).compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Single<Boolean> signup(String phone, String password, String repassword, String nickname) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, String>>) emitter -> {
            if (!VerifyUtils.isPhoneVerified(phone)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PHONE_NUMBER_ERROR));
            } else if (!VerifyUtils.isLengthVerified(password, 6, 16)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PASSWORD_ERROR));
            } else if (!VerifyUtils.isLengthVerified(repassword, 6, 16)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_REPEAT_PASSWORD_ERROR));
            } else if (!(!TextUtils.isEmpty(password) && password.equals(repassword))) {
                emitter.onError(new ApiException(ResponseCode.NOT_SAME_ERROR));
            } else if (!VerifyUtils.isLengthVerified(nickname, 0, 10)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_NICKNAME_ERROR));
            }
            Map<String, String> params = new HashMap<>();
            params.put(Key.USERNAME, phone);
            params.put(Key.PASSWORD, password);
            params.put(Key.REPASSWORD, repassword);
            params.put(Key.NICKNAME, nickname);
            emitter.onSuccess(params);
        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)
                .singup(JsonUtils.mapToRequestBody2(result))
                .map(RxUtils.mappingResponseToResult(String.class))
                .compose(RxUtils.mappingResultToCheck())
                .compose(RxUtils.rxSchedulerHelper()));
    }

    @Override
    public Single<Boolean> updatePassword(String phoneNumber, String password, String repassword) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return Single.create((SingleOnSubscribe<Map<String, Object>>) emitter -> {
            if (!VerifyUtils.isPhoneVerified(phoneNumber)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PHONE_NUMBER_ERROR));
            } else if (!VerifyUtils.isLengthVerified(password, 6, 16)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_PASSWORD_ERROR));
            } else if (!VerifyUtils.isLengthVerified(repassword, 6, 16)) {
                emitter.onError(new ApiException(ResponseCode.INPUT_REPEAT_PASSWORD_ERROR));
            } else if (!(!TextUtils.isEmpty(password) && password.equals(repassword))) {
                emitter.onError(new ApiException(ResponseCode.NOT_SAME_ERROR));
            }
            Map<String, Object> params = new HashMap<>();
            params.put(Key.USERNAME, phoneNumber);
            params.put(Key.PASSWORD, password);
            emitter.onSuccess(params);
        }).flatMap(result -> httpHelper.obtainRetrofitService(UserService.class)
                .updatePassword(JsonUtils.mapToRequestBody(result))
                .map(RxUtils.mappingResponseToResult(String.class))
                .compose(RxUtils.mappingResultToCheck()))
                .compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Single<Boolean> isCurrentUserLogin() {
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.just(!TextUtils.isEmpty(preferencesHelper.getCurrentUserToken()));
    }

    @Override
    public void clearUserInfo() {
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        preferencesHelper.clearUserInfo();
    }

    @Override
    public Single<Result> logout() {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return httpHelper.obtainRetrofitService(UserService.class)
                .logout()
                .map(RxUtils.mappingResponseToResult(Boolean.class))
                .doOnSuccess(consumer -> clearUserInfo())
                .compose(RxUtils.rxSchedulerHelper());
    }

    @Override
    public Single<String> updateUserProfile(String url) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        PreferencesHelper preferencesHelper = mRepositoryHelper.getPreferencesHelper();
        return Single.<Map<String, Object>>create(emitter -> {
            long userId = preferencesHelper.getCurrentUserId();
            if (userId == 0L) {
                throw new ApiException(ResponseCode.TOKEN_ERROR);
            } else if (TextUtils.isEmpty(url)) {
                throw new ApiException(ResponseCode.ILLEGAL_ARGUMENT);
            }
            Map<String, Object> map = new HashMap<>();
            map.put(Key.ID, userId);
            map.put(Key.HEADURL, url);
            emitter.onSuccess(map);
        }).flatMap(params -> httpHelper.obtainRetrofitService(UserService.class)
                .update(JsonUtils.mapToRequestBody(params))
                .map(RxUtils.mappingResponseToResult(String.class))
                .map(result -> {
                    if (result.getStatus() == ResponseCode.SUCCESS.getStatus()) {
                        return url;
                    } else {
                        throw new ApiException(result.getStatus(), result.getMsg());
                    }
                }).doOnSuccess(preferencesHelper::saveCurrentUserIcon)
                .compose(RxUtils.rxSchedulerHelper()));
    }

    public RepositoryHelper getRepositoryHelper() {
        return mRepositoryHelper;
    }
}
