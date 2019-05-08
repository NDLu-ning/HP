package com.graduation.hp.repository.model;

import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.repository.http.entity.vo.UserVO;
import com.graduation.hp.repository.http.entity.wrapper.UserVOWrapper;

import io.reactivex.Single;

public interface IUserModel {

    Single<Integer> sendVerificationMessage(String phone);

    Single<UserVO> getCurrentInfo();

    Single<UserVOWrapper> getUserInfo(long userId);

    Single<UserVO> login(String username, String password);

    Single<Boolean> signup(String username,String password, String repassword, String phone);

    Single<Boolean> updatePassword(String phoneNumber, String password, String repassword);

    Single<Boolean> isCurrentUserLogin();

    void clearUserInfo();

    Single<Result> logout();

    Single<Boolean> updateUserInfo();
}
