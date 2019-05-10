package com.graduation.hp.core.repository.http.params;

import com.graduation.hp.core.repository.http.exception.ApiException;

import okhttp3.Request;

/**
 * 配置框架---参数添加类
 * Created by Ning on 2019/2/4.
 */

public interface ParamsAdder {

    void addParams(Request request, Request.Builder newBuilder) throws ApiException;


    ParamsAdder DEFAULT = new ParamsAdder() {

        @Override
        public void addParams(Request request, Request.Builder newBuilder) throws ApiException {

        }
    };
}
