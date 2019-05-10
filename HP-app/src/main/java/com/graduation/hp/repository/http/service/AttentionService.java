package com.graduation.hp.repository.http.service;


import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 关注接口
 */
public interface AttentionService {

    @Headers(value = {"Content-Type:application/json"})
    @POST("focus/countFocusNumber")
    Single<String> countAttentionNumber(@Body RequestBody requestBody);

    @Headers(value = {"Content-Type:application/json", "Cookie-Name:token"})
    @POST("focus/save")
    Single<String> focusUser(@Body RequestBody requestBody);

    @Headers(value = {"Content-Type:application/json", "Cookie-Name:token"})
    @POST("focus/dataGrid")
    Single<String> focusDataGrid(@Body RequestBody requestBody);

    @Headers(value = {"Content-Type:application/json", "Cookie-Name:token"})
    @POST("focus/isFocus")
    Single<String> isFocus(@Body RequestBody requestBody);
}
