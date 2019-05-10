package com.graduation.hp.core.repository.http.interceptor;


import com.graduation.hp.core.repository.http.log.HttpLogger;
import com.graduation.hp.core.utils.LogUtils;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static com.alibaba.fastjson.util.IOUtils.UTF8;

/**
 * 配置框架---调试拦截器
 * Created by Ning on 2018/11/20.
 */
@Singleton
public class DebugInterceptor implements Interceptor {

    private static final String START_LINE = "   ────── REQUEST START ──────────────────────────────────────────────────────────────────────────";
    private static final String LOG_POSITION = "Log_Headers: %s ; Log_Request: %s ; Log_Response: %s ;";
    private static final String HEADERS_UP_LINE = "   ────── HEADERS ────────────────────────────────────────────────────────────────────────";
    private static final String REQUEST_UP_LINE = "   ────── REQUEST ────────────────────────────────────────────────────────────────────────";
    private static final String END_LINE = "   ────── REQUEST END ───────────────────────────────────────────────────────────────────────────";
    private static final String RESPONSE_UP_LINE = "   ────── RESPONSE ───────────────────────────────────────────────────────────────────────";
    private static final String URL_TAG = "URL: ";
    private static final String HTTP_ERROR = "HTTP ERROR: ";
    private static final String LOCALHOST = "LOCALHOST";

    // TODO 获取传输参数，并将其转换为Map
    @Inject
    @Named("debug")
    boolean debug;

    @Inject
    HttpLogger logger;

    @Inject
    Level level;

    /**
     * 调试输出信息等级
     * 用于确认输出对象
     */
    public enum Level {
        NONE,       /* 不打印任何信息 */
        HEADER,     /* 打印请求路径，请求头 */
        REQUEST,    /* 打印请求路径，请求方式，请求参数 */
        RESPONSE,   /* 打印请求路径，响应码，响应信息 */
        BASIC,      /* 打印请求路径，请求方式，响应码，响应信息 */
        ALL         /* 打印请求路径，请求方式，请求头，响应码，相应信息 */
    }

    @Inject
    public DebugInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Level level = this.level;
        if (level == Level.NONE && !debug) {
            return chain.proceed(request);
        }
        RequestBody requestBody = request.body();
        boolean logHeader = level == Level.HEADER || level == Level.ALL;
        boolean logRequest = level != Level.RESPONSE && level != Level.NONE;
        boolean logResponse = level != Level.HEADER && level != Level.REQUEST && level != Level.NONE;
        LogUtils.d(START_LINE);
        LogUtils.d(String.format(LOG_POSITION, logHeader + "", logRequest + "", logResponse + ""));
        LogUtils.d(REQUEST_UP_LINE);
        LogUtils.d(URL_TAG + request.url());
        if (logRequest) {
            Connection connection = chain.connection();
            String protocol = connection != null ? connection.protocol().toString() : Protocol.HTTP_1_1.toString();
            String method = request.method();
            logger.printRequest(protocol, method, getParams(request));
        }
        if (logHeader) {
            LogUtils.d(HEADERS_UP_LINE);
            Headers headers = request.headers();
            String content_type = "";
            long content_length = 0L;
            if (requestBody != null) {
                if (requestBody.contentType() != null) {
                    content_type = requestBody.contentType().toString();
                }
                if (requestBody.contentLength() != -1) {
                    content_length = requestBody.contentLength();
                }

            }
            logger.printHeaders(content_type, content_length, headers);
        }
        long startMs = System.currentTimeMillis();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            LogUtils.d(HTTP_ERROR + e);
            throw e;
        }
        long tookMs = System.currentTimeMillis() - startMs;
        ResponseBody responseBody = response.body();
        if (logResponse) {
            long contentLength = responseBody.contentLength();
            LogUtils.d(RESPONSE_UP_LINE);
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            if (!isPlaintext(buffer)) {
                LogUtils.d("");
                LogUtils.d("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                return response;
            }
            logger.printResponse(tookMs, contentLength != -1 ? contentLength + "-byte" : "unknown-length", response.code(), contentLength != 0 ?
                    buffer.clone().readString(charset) : "");

        }
        LogUtils.d(END_LINE);
        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    private String getParams(Request request) throws IOException {
        String params = "";
        if (request.method().equals("POST")) {
            RequestBody requestBody = request.body();
            Charset charset = UTF8;
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            if (!(requestBody instanceof MultipartBody)) {
                params = buffer.readString(charset);
            }
        } else if (request.method().equals("GET")) {
            params = request.url().query();
        }
        return params;
    }
}
