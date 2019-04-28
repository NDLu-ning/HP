package com.graduation.hp.repository.http.service;

import com.graduation.hp.core.repository.http.bean.Result;

import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("")
    Single<Result> login();
}
