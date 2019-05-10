package com.graduation.hp.repository.http.service;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
/**
 * 评论接口
 */
public interface DiscussService {

    @Headers(value = {"Content-Type:application/json", "Cookie-Name:token"})
    @POST("article/discuss")
    Single<String> discuss(@Body RequestBody body);

    @Headers(value = {"Content-Type:application/json"})
    @POST("article/getDiscuss")
    Single<String> getDiscuss(@Body RequestBody body);


    @Headers(value = {"Content-Type:application/json"})
    @POST("invitation/getDiscuss")
    Single<String> getInvitationDiscuss(@Body RequestBody body);


    @Headers(value = {"Content-Type:application/json", "Cookie-Name:token"})
    @POST("invitation/getDiscuss")
    Single<String> discussInvitation(@Body RequestBody body);
}
