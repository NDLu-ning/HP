package com.graduation.hp.core.repository.http.url.listener;

import okhttp3.HttpUrl;

/**
 * Created by Ning on 2019/2/4.
 */

public interface OnUrlChangeListener {
    void onUrlChangeBefore(HttpUrl url, String domainName);

    void onUrlChanged(HttpUrl newUrl, HttpUrl url);
}
