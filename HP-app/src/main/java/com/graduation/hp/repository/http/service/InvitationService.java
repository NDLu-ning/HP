package com.graduation.hp.repository.http.service;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 帖子接口
 */
public interface InvitationService {

    @Headers(value = {"Content-Type:application/json","Cookie-Name:token","Need-Valid:false"})
    @POST("invitation/dataGrid")
    Single<String> dataGrid(@Body RequestBody body);

    @Headers(value = {"Content-Type:application/json","Cookie-Name:token","Need-Valid:false"})
    @POST("invitation/queryByCondition")
    Single<String> queryByCondition(@Body RequestBody body);

    @Headers(value = {"Content-Type:application/json", "Cookie-Name:token"})
    @POST("invitation/saveData")
    Single<String> saveData(@Body RequestBody body);
}
