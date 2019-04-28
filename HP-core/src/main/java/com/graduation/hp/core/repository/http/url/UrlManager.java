package com.graduation.hp.core.repository.http.url;

import android.text.TextUtils;
import android.util.Log;

import com.graduation.hp.core.repository.http.url.listener.OnUrlChangeListener;
import com.graduation.hp.core.repository.http.url.parser.DefaultUrlParser;
import com.graduation.hp.core.repository.http.url.parser.UrlParser;
import com.graduation.hp.core.utils.Preconditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ning on 2019/2/4.
 */

public class UrlManager {

    private static final String TAG = UrlManager.class.getSimpleName();

    private static final String DOMAIN_NAME = "Domain-Name";
    private static final String GLOBAL_DOMAIN_NAME = "Global-Domain-Name";

    public static final String DOMAIN_NAME_HEADER = DOMAIN_NAME + ": ";
    public static final String IDENTIFICATION_IGNORE = "#url_ignore";
    private HttpUrl baseUrl;
    private int pathSize;
    private boolean isRun = false;
    private boolean debug = true;
    private final Map<String, HttpUrl> mDomainMap = new HashMap<>();
    private UrlParser mUrlParser;

    private final Interceptor mInterceptor;

    private final List<OnUrlChangeListener> mListeners = new ArrayList<>();

    private UrlManager() {
        UrlParser urlParser = new DefaultUrlParser();
        urlParser.init(this);
        setUrlParser(urlParser);
        this.mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                if (!isRun()) // 可以在 App 运行时, 随时通过 setRun(false) 来结束本框架的运行
                    return chain.proceed(chain.request());
                return chain.proceed(processRequest(chain.request()));
            }
        };
    }

    public static UrlManager getInstance() {
        return UrlManagerHolder.INSTANCE;
    }

    private Request processRequest(Request request) {
        if (request == null) return request;

        Request.Builder newBuilder = request.newBuilder();

        String url = request.url().toString();
        //如果 Url 地址中包含 IDENTIFICATION_IGNORE 标识符, 框架将不会对此 Url 进行任何切换 BaseUrl 的操作
        if (url.contains(IDENTIFICATION_IGNORE)) {
            return appendSourceUrl(newBuilder, url);
        }
        String domainName = obtainDomainNameFromHeaders(request);

        HttpUrl httpUrl;

        Object[] listeners = listenersToArray();

        // 如果有 header,获取 header 中 domainName 所映射的 url,若没有,则检查全局的 BaseUrl,未找到则为null
        if (!TextUtils.isEmpty(domainName)) {
            notifyListener(request, domainName, listeners);
            httpUrl = fetchDomain(domainName);
            newBuilder.removeHeader(DOMAIN_NAME);
        } else {
            notifyListener(request, GLOBAL_DOMAIN_NAME, listeners);
            httpUrl = getGlobalDomain();
        }
        if (null != httpUrl) {
            HttpUrl newUrl = mUrlParser.parseUrl(httpUrl, request.url());
            if (debug)
                Log.d(TAG, "The new url is { " + newUrl.toString() + " }, old url is { " + request.url().toString() + " }");
            if (listeners != null) {
                for (int i = 0; i < listeners.length; i++) {
                    ((OnUrlChangeListener) listeners[i]).onUrlChanged(newUrl, request.url()); // 通知监听器此 Url 的 BaseUrl 已被切换
                }
            }
            return newBuilder
                    .url(newUrl)
                    .build();
        }
        return newBuilder.build();
    }

    public OkHttpClient.Builder with(OkHttpClient.Builder builder) {
        Preconditions.checkNotNull(builder, "builder cannot be null");
        return builder.addInterceptor(mInterceptor);
    }

    private String obtainDomainNameFromHeaders(Request request) {
        List<String> headers = request.headers(DOMAIN_NAME);
        if (headers == null || headers.size() == 0) {
            return null;
        }
        if (debug) {
            for (String str : headers) {
                Log.d(TAG, "obtainDomainNameFromHeaders: " + str);
            }
        }
        if (headers.size() > 1) {
            throw new IllegalArgumentException("Only one Domain-Name in the headers");
        }
        return request.header(DOMAIN_NAME);
    }

    public synchronized HttpUrl fetchDomain(String domainName) {
        Preconditions.checkNotNull(domainName, "domainName cannot be null");
        return mDomainMap.get(domainName);
    }

    public void startAdvancedModel(String baseUrl) {
        Preconditions.checkNotNull(baseUrl, "baseUrl cannot be null");
        startAdvancedModel(HttpUrl.parse(Preconditions.checkNotNull(baseUrl, "this url is null")));
    }

    public synchronized void startAdvancedModel(HttpUrl baseUrl) {
        Preconditions.checkNotNull(baseUrl, "baseUrl cannot be null");
        this.baseUrl = baseUrl;
        this.pathSize = baseUrl.pathSize();
        List<String> baseUrlpathSegments = baseUrl.pathSegments();
        if ("".equals(baseUrlpathSegments.get(baseUrlpathSegments.size() - 1))) {
            this.pathSize -= 1;
        }
    }

    /**
     * 获取 PathSegments 的总大小
     *
     * @return PathSegments 的 size
     */
    public int getPathSize() {
        return pathSize;
    }

    /**
     * 是否开启高级模式
     *
     * @return {@code true} 为开启, {@code false} 为未开启
     */
    public boolean isAdvancedModel() {
        return baseUrl != null;
    }

    /**
     * 获取 BaseUrl
     *
     * @return {@link #baseUrl}
     */
    public HttpUrl getBaseUrl() {
        return baseUrl;
    }


    /**
     * 获取全局 BaseUrl
     */
    public synchronized HttpUrl getGlobalDomain() {
        return mDomainMap.get(GLOBAL_DOMAIN_NAME);
    }


    /**
     * 拼接原请求路径
     *
     * @param builder
     * @param url
     * @return
     */
    private Request appendSourceUrl(Request.Builder builder, String url) {
        String[] split = url.split(IDENTIFICATION_IGNORE);
        StringBuffer buffer = new StringBuffer();
        for (String s : split) {
            buffer.append(s);
        }
        return builder
                .url(buffer.toString())
                .build();
    }

    /**
     * 注册监听器(当 Url 的 BaseUrl 被切换时会被回调的监听器)
     *
     * @param listener 监听器列表
     */
    public void registerUrlChangeListener(OnUrlChangeListener listener) {
        Preconditions.checkNotNull(listener, "listener cannot be null");
        synchronized (mListeners) {
            mListeners.add(listener);
        }
    }

    /**
     * 注销监听器(当 Url 的 BaseUrl 被切换时会被回调的监听器)
     *
     * @param listener 监听器列表
     */
    public void unregisterUrlChangeListener(OnUrlChangeListener listener) {
        Preconditions.checkNotNull(listener, "listener cannot be null");
        synchronized (mListeners) {
            mListeners.remove(listener);
        }
    }

    private Object[] listenersToArray() {
        Object[] listeners = null;
        synchronized (mListeners) {
            if (mListeners.size() > 0) {
                listeners = mListeners.toArray();
            }
        }
        return listeners;
    }

    private void notifyListener(Request request, String domainName, Object[] listeners) {
        if (listeners != null) {
            for (int i = 0; i < listeners.length; i++) {
                ((OnUrlChangeListener) listeners[i]).onUrlChangeBefore(request.url(), domainName);
            }
        }
    }

    /**
     * 可自行实现 {@link UrlParser} 动态切换 Url 解析策略
     *
     * @param parser {@link UrlParser}
     */
    public void setUrlParser(UrlParser parser) {
        Preconditions.checkNotNull(parser, "parser cannot be null");
        this.mUrlParser = parser;
    }

    public boolean isRun() {
        return this.isRun;
    }

    public void setRun(boolean run) {
        this.isRun = run;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * 存放 Domain(BaseUrl) 的映射关系
     *
     * @param domainName
     * @param domainUrl
     */
    public void putDomain(String domainName, String domainUrl) {
        Preconditions.checkNotNull(domainName, "domainName cannot be null");
        Preconditions.checkNotNull(domainUrl, "domainUrl cannot be null");
        synchronized (mDomainMap) {
            mDomainMap.put(domainName, HttpUrl.parse(Preconditions.checkNotNull(domainUrl, "this url is null")));
        }
    }

    public void removeDomain(String domainName) {
        Preconditions.checkNotNull(domainName, "domainName cannot be null");
        synchronized (mDomainMap) {
            mDomainMap.remove(domainName);
        }
    }

    public void setGlobalDomain(String globalDomain) {
        Preconditions.checkNotNull(globalDomain, "globalDomain cannot be null");
        synchronized (mDomainMap) {
            mDomainMap.put(GLOBAL_DOMAIN_NAME, HttpUrl.parse(Preconditions.checkNotNull(globalDomain, "this url is null")));
        }
    }

    /**
     * 移除全局 BaseUrl
     */
    public void removeGlobalDomain() {
        synchronized (mDomainMap) {
            mDomainMap.remove(GLOBAL_DOMAIN_NAME);
        }
    }

    public synchronized boolean haveDomain(String domainName) {
        return mDomainMap.containsKey(domainName);
    }

    /**
     * 清理所有 Domain(BaseUrl)
     */
    public void clearAllDomain() {
        mDomainMap.clear();
    }

    private static final class UrlManagerHolder {
        private static final UrlManager INSTANCE = new UrlManager();
    }
}
