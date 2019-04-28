package com.graduation.hp.repository.http.service;

import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NewsService {


    @FormUrlEncoded
    @POST("")
    Single<String> getNewsListByCategory(String category, int page, int limit);

}
