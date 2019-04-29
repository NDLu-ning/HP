package com.graduation.hp.repository.model;

import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.repository.http.entity.User;

import io.reactivex.Single;

public interface IUserModel {

    Single<String> sendVerificationMessage(String phone);

    Single<User> login(String username, String password);

    Single<Boolean> signup(String username, String password, String repassword, String phone);

    Single<Boolean> isCurrentUserLogin();

    Single<Result> clearUserInfo();
}
