package com.graduation.hp.repository.http.service;

import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NewsService {

    @Headers(value = {"Content-Type:application/json"})
    @POST("")
    Single<String> getNewsListByCategory(long category, int page, int limit);

}
