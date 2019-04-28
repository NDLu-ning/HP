package com.graduation.hp.core.repository.http.interceptor;

import android.text.TextUtils;

import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.repository.http.params.ParamsAdder;
import com.graduation.hp.core.repository.http.validator.DataValidator;
import com.graduation.hp.core.utils.JsonUtils;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Ning on 2019/2/4.
 */

public class RequestInterceptor implements Interceptor {

    private static final String COOKIE_NAME = "Cookie-Name";

    @Inject
    List<DataValidator> dataValidators;

    @Inject
    List<ParamsAdder> paramsAdders;

    @Inject
    @Named("validate")
    boolean validate;

    @Inject
    public RequestInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();
        for (ParamsAdder paramsAdder : paramsAdders) {
            try {
                paramsAdder.addParams(request, newBuilder);
            } catch (ApiException e) {
                return getResponse(request, JsonUtils.objectToJson(Result.build(ResponseCode.TOKEN_ERROR)));
            }
        }


        return chain.proceed(newBuilder.build());
    }

    protected Response getResponse(Request request, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .build();
    }
}
