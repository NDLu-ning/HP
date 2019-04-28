package com.graduation.hp.core.repository.http.url.parser;

import com.graduation.hp.core.repository.http.url.UrlManager;

import okhttp3.HttpUrl;

/**
 * Created by Ning on 2019/2/4.
 */

public class DefaultUrlParser implements UrlParser {

    private UrlParser mDomainUrlParser;
    private volatile UrlParser mAdvancedUrlParser;
    private volatile UrlParser mSuperUrlParser;
    private UrlManager mUrlManager;

    @Override
    public void init(UrlManager urlManager) {
        this.mUrlManager = urlManager;
        this.mDomainUrlParser = new DomainUrlParser();
        this.mDomainUrlParser.init(mUrlManager);
    }

    @Override
    public HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url) {
        if (null == domainUrl) return url;
        if (mUrlManager.isAdvancedModel()) {
            if (mAdvancedUrlParser == null) {
                synchronized (this) {
                    if (mAdvancedUrlParser == null) {
                        mAdvancedUrlParser = new AdvancedUrlParser();
                        mAdvancedUrlParser.init(mUrlManager);
                    }
                }
            }
            return mAdvancedUrlParser.parseUrl(domainUrl, url);
        }
        return mDomainUrlParser.parseUrl(domainUrl, url);
    }
}
