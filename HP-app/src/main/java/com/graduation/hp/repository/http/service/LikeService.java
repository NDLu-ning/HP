package com.graduation.hp.repository.http.service;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 *
 */
public interface LikeService {

    @Headers(value = {"Content-Type:application/json", "Cookie-Name:token"})
    @POST("article/like")
    Single<String> like(@Body RequestBody requestBody);


    @Headers(value = {"Content-Type:application/json", "Cookie-Name:token"})
    @POST("invitation/like")
    Single<String> likeInvitation(@Body RequestBody requestBody);
}
