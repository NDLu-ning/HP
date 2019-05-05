package com.graduation.hp.repository.http.service;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CommentService {

    @Headers(value = {"Content-Type:application/json", "Cookie-Name:token"})
    @POST("article/discuss")
    Single<String> discuss(@Body RequestBody body);

}
