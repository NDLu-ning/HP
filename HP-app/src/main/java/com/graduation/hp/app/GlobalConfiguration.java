package com.graduation.hp.app;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.graduation.hp.core.app.base.lifecycleable.AppLifecycle;
import com.graduation.hp.core.app.di.module.ConfigModule;
import com.graduation.hp.core.app.di.module.GlobalConfigModule;
import com.graduation.hp.core.repository.http.interceptor.DebugInterceptor;
import com.graduation.hp.repository.http.params.TokenAdder;

import java.util.List;

public class GlobalConfiguration implements ConfigModule {


    @Override
    public void applyOptions(Context ctx, GlobalConfigModule.Builder configBuilder) {
        configBuilder
                .runUrlManager(true)
                .debug(true)
                .validate(true)
                .startAdvancedUrl("http://47.52.157.78:8080/")
                .addParamsAdder(new TokenAdder())
                .level(DebugInterceptor.Level.BASIC)
                .okHttpConfiguration((context, builder) -> {

                }).retrofitConfiguration((context, builder) -> {

                });

    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycle> lifecycles) {
        lifecycles.add(new AppLifecycleCallbacksImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {

    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {

    }
}
