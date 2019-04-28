package com.graduation.hp.core.repository.http;

import android.support.annotation.NonNull;
import android.util.LruCache;


import com.graduation.hp.core.utils.Preconditions;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by Ning on 2018/11/15.
 */
@Singleton
public class HttpHelperImpl implements HttpHelper {

    @Inject
    Lazy<Retrofit> mRefrofit;

    private LruCache<String, Object> mRetrofitServiceCache;


    @Inject
    public HttpHelperImpl() {
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
     */
    @NonNull
    @Override
    public synchronized <T> T obtainRetrofitService(@NonNull Class<T> serviceClass) {
        return createWrapperService(serviceClass);
    }

    private <T> T createWrapperService(final Class<T> serviceClass) {
        Preconditions.checkNotNull(serviceClass, "service == null");

        // 二次代理
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, final Method method, final Object[] args)
                            throws Throwable {
                        if (method.getReturnType() == Observable.class) {
                            return Observable.defer(() -> {
                                final T service = getRetrofitService(serviceClass);
                                return ((Observable) getRetrofitMethod(service, method)
                                        .invoke(service, args));
                            });
                        } else if (method.getReturnType() == Single.class) {
                            return Single.defer(() -> {
                                final T service = getRetrofitService(serviceClass);
                                return ((Single) getRetrofitMethod(service, method)
                                        .invoke(service, args));
                            });
                        }
                        // 返回值不是 Observable 或 Single 的话不处理。
                        final T service = getRetrofitService(serviceClass);
                        return getRetrofitMethod(service, method).invoke(service, args);
                    }
                });
    }

    private <T> T getRetrofitService(Class<T> serviceClass) {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = new LruCache<>(10);
        }
        T retrofixService = (T) mRetrofitServiceCache.get(serviceClass.getCanonicalName());
        if (retrofixService == null) {
            retrofixService = mRefrofit.get().create(serviceClass);
            mRetrofitServiceCache.put(serviceClass.getCanonicalName(), retrofixService);
        }
        return retrofixService;
    }

    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }

}
