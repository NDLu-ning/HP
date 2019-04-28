package com.graduation.hp.core.repository.http.url.parser;


import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.graduation.hp.core.repository.http.url.UrlManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;

/**
 * Created by Ning on 2019/2/6.
 */

public class AdvancedUrlParser implements UrlParser {
    private UrlManager mUrlManager;
    private LruCache<String, String> mCache;

    @Override
    public void init(UrlManager retrofitUrlManager) {
        this.mUrlManager = retrofitUrlManager;
        this.mCache = new LruCache<>(100);
    }

    @Override
    public HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url) {
        if (null == domainUrl) return url;

        HttpUrl.Builder builder = url.newBuilder();

        if (TextUtils.isEmpty(mCache.get(getKey(domainUrl, url)))) {
//            LogUtils.d(url.encodedPath());// /rest/user/info/basic
            for (int i = 0; i < url.pathSize(); i++) {
//                LogUtils.d(url.pathSegments().get(i)); // rest
                // user ...
                //当删除了上一个 index, PathSegment 的 item 会自动前进一位, 所以 remove(0) 就好
                builder.removePathSegment(0);
            }
            List<String> newPathSegments = new ArrayList<>();
            newPathSegments.addAll(domainUrl.encodedPathSegments());

            if (url.pathSize() > mUrlManager.getPathSize()) {
                List<String> encodedPathSegments = url.encodedPathSegments();
                for (int i = mUrlManager.getPathSize(); i < encodedPathSegments.size(); i++) {
                    newPathSegments.add(encodedPathSegments.get(i));
                }
            } else if (url.pathSize() < mUrlManager.getPathSize()) {
                throw new IllegalArgumentException(String.format("Your final path is %s, but the baseUrl of your RetrofitUrlManager#startAdvancedModel is %s",
                        url.scheme() + "://" + url.host() + url.encodedPath(),
                        mUrlManager.getBaseUrl().scheme() + "://"
                                + mUrlManager.getBaseUrl().host()
                                + mUrlManager.getBaseUrl().encodedPath()));
            }

            for (String PathSegment : newPathSegments) {
                builder.addEncodedPathSegment(PathSegment);
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
        return domainUrl.encodedPath() + url.encodedPath()
                + mUrlManager.getPathSize();
    }
}
