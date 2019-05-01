package com.graduation.hp.repository.model;

import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.repository.http.entity.local.UserVo;

import io.reactivex.Single;

public interface IUserModel {

    Single<String> sendVerificationMessage(String phone);

    Single<User> getCurrentInfo();

    Single<UserVo> getUserInfo(long userId);

    Single<User> login(String username, String password);

    Single<Boolean> signup(String username, String password, String repassword, String phone);

    Single<Boolean> updatePassword(String phoneNumber, String password, String repassword);

    Single<Boolean> isCurrentUserLogin();

    Single<Result> clearUserInfo();
}
