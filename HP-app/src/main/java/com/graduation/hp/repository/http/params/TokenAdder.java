package com.graduation.hp.repository.http.params;

import android.text.TextUtils;

import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.repository.http.params.BaseParamsAdder;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.repository.preferences.PreferencesHelper;
import com.graduation.hp.repository.preferences.PreferencesHelperImpl;

import okhttp3.Cookie;
import okhttp3.Request;

public class TokenAdder extends BaseParamsAdder {

    private final PreferencesHelper mPreferencesHelper;

    public TokenAdder() {
        mPreferencesHelper = PreferencesHelperImpl.getInstance();
    }

    private static final String COOKIE_NAME = "Cookie-Name";
    private static final String TOKEN_KEY = "token";

    @Override
    public void addParams(Request request, Request.Builder newBuilder) throws ApiException {
        try {
            String cookieName = obtainCookieNameFromHeaders(COOKIE_NAME, request);
            // 是否添加Token至Cookie
            if (!TextUtils.isEmpty(cookieName)) {
                String token = mPreferencesHelper.getCurrentUserToken();
                newBuilder.removeHeader(COOKIE_NAME);
                if (TextUtils.isEmpty(token)) {
                    throw new ApiException(ResponseCode.TOKEN_ERROR);
                }
                newBuilder.addHeader(TOKEN_KEY, token);
            }
        } catch (IllegalArgumentException e) {
        }
    }


}
