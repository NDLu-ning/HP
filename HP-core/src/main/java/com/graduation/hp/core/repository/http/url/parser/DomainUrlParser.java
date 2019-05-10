package com.graduation.hp.core.repository.http.url.parser;


import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.graduation.hp.core.repository.http.url.UrlManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;

/**
 * 配置框架---URL切换第一种模式
 * 只用于切换URL
 * 例如：设置的基础URL为http://localhost:8080，原路径名为http://localhost:8080/hp/api
 *      需要替换的URL为http://192.168.119.123:8080
 *      替换的结果URL为http://192.168.119.123:8080/hp/api
 * Created by Ning on 2019/2/4.
 */

public class DomainUrlParser implements UrlParser {

    private LruCache<String, String> mCache;


    @Override
    public void init(UrlManager urlManager) {
        mCache = new LruCache<>(100);
    }

    @Override
    public HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url) {
        if (null == domainUrl) return url;

        HttpUrl.Builder builder = url.newBuilder();

        if (TextUtils.isEmpty(mCache.get(getKey(domainUrl, url)))) {
            for (int i = 0; i < url.pathSize(); i++) {
                //当删除了上一个 index, PathSegment 的 item 会自动前进一位, 所以 remove(0) 就好
                builder.removePathSegment(0);
            }

            List<String> newPathSegments = new ArrayList<>();
            newPathSegments.addAll(domainUrl.encodedPathSegments());
            newPathSegments.addAll(url.encodedPathSegments());

            for (String pathSegment : newPathSegments) {
                builder.addEncodedPathSegment(pathSegment);
            }
        } else {
            builder.encodedPath(mCache.get(getKey(domainUrl, url)));
        }

        HttpUrl httpUrl = builder
                .scheme(domainUrl.scheme())
                .host(domainUrl.host())
                .port(domainUrl.port())
                .build();

        if (TextUtils.isEmpty(mCache.get(getKey(domainUrl, url)))) {
            mCache.put(getKey(domainUrl, url), httpUrl.encodedPath());
        }
        return httpUrl;
    }

    private String getKey(HttpUrl domainUrl, HttpUrl url) {
        return domainUrl.encodedPath() + url.encodedPath();
    }
}
