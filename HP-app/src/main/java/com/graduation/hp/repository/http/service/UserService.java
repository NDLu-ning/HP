package com.graduation.hp.repository.http.service;

import com.graduation.hp.core.repository.http.bean.Result;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("user/login")
    Single<String> login(@Field("username")String username,@Field("password")String password);

    @FormUrlEncoded
    @POST("")
    Single<String> singup();

    @Headers("Cookie-Name:token")
    @FormUrlEncoded
    @POST
    Single<String> getUserInfo();
}
