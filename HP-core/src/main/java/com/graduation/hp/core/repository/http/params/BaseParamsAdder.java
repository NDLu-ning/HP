package com.graduation.hp.core.repository.http.params;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class BaseParamsAdder implements ParamsAdder {

    protected String obtainCookieNameFromHeaders(String cookieName, Request request) {
        List<String> headers = request.headers(cookieName);
        if (headers == null || headers.size() == 0) {
            return null;
        }
        if (headers.size() > 1) {
            throw new IllegalArgumentException("Only one Cookie-Name in the headers");
        }
        return request.header(cookieName);
    }
}
