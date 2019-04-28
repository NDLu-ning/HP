package com.graduation.hp.core.repository.http.params;

import com.bumptech.glide.RequestBuilder;
import com.graduation.hp.core.repository.http.exception.ApiException;

import java.util.Map;

import okhttp3.Request;

/**
 * Created by Ning on 2019/2/4.
 */

public interface ParamsAdder {

    void addParams(Request request, Request.Builder newBuilder) throws ApiException;


    ParamsAdder DEFAULT = new ParamsAdder() {

        @Override
        public void addParams(Request request, Request.Builder newBuilder) {

        }
    };
}
