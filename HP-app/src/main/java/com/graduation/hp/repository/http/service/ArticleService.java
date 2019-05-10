package com.graduation.hp.repository.http.service;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 文章接口
 */
public interface ArticleService {

    @Headers(value = {"Content-Type:application/json"})
    @POST("article/dataGrid")
    Single<String> dataGrid(@Body RequestBody body);

    @Headers(value = {"Content-Type:application/json"})
    @POST("article/queryByCondition")
    Single<String> queryByCondition(@Body RequestBody body);
}
