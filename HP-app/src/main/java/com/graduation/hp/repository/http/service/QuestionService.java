package com.graduation.hp.repository.http.service;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface QuestionService {

    @Headers(value = {"Content-Type:application/json"})
    @POST("question/getAllQuestion")
    Single<String> getAllQuestion(@Query("type") int type);

    @Headers(value = {"Content-Type:application/json"})
    @POST("")
    Single<String> commit(@Body RequestBody body);
}

