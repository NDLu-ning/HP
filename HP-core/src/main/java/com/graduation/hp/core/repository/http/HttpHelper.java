package com.graduation.hp.core.repository.http;

import android.support.annotation.NonNull;

/**
 * Created by Ning on 2018/11/15.
 */

public interface HttpHelper {

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service Retrofit service class
     * @param <T>     Retrofit service 类型
     * @return Retrofit service
     */
    @NonNull
    <T> T obtainRetrofitService(@NonNull Class<T> service);


}
