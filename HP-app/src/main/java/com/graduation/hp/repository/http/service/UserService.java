package com.graduation.hp.repository.http.service;

import com.graduation.hp.core.repository.http.bean.Result;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @Headers({"Content-Type:application/json"})
    @POST("user/login")
    Single<String> login(@Body RequestBody requestBody);

    @Headers(value = {"Content-Type:application/json"})
    @POST("user/signup")
    Single<String> singup(@Body RequestBody requestBody);

    @Headers(value = {"Content-Type:application/json", "Cookie-Name:token"})
    @POST("user/queryByCondition")
    Single<String> getUserInfo(@Body RequestBody requestBody);

    @Headers(value = {"Content-Type:application/json"})
    @POST("user")
    Single<String> updatePassword(@Body RequestBody requestBody);
}
