package com.graduation.hp.repository.model;

import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.repository.http.entity.User;

import io.reactivex.Single;

public interface IUserModel {

    Single<String> sendVerificationMessage(String phone);

    Single<Result> login(String username, String password);

    Single<Result> saveCurrentUserInfo(User user);

    Single<Result> signup();

    Single<Boolean> isCurrentUserLogin();

    Single<Result> clearUserInfo();
}
