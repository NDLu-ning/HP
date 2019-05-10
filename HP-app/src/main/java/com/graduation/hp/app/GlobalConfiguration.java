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

/**
 * 在Manifest进行注册，即可对应用使用的插件进行配置
 */
public class GlobalConfiguration implements ConfigModule {


    @Override
    public void applyOptions(Context ctx, GlobalConfigModule.Builder configBuilder) {
        configBuilder
                .runUrlManager(true) // 是否需要运行时URL切换
                .debug(true)         // 是否需要调试网络数据
                .validate(true)      // 是否需要数据验证
                .startAdvancedUrl("http://47.52.157.78:8080/")  // 第二种模式的基本URL，切换URL的时候被覆盖URL
                .multiDomain("UPLOAD_URL", "http://39.106.49.168:8080/") // 第二种模式的替换URL，即只需要在请求头中设置Domain-Name:UPLOAD_URL,
                                                                                    // 访问原路径为http://47.52.157.78：8080/upload
                                                                                    // 就会被替换为http://39.106.49.168:8080/upload
                .addParamsAdder(new TokenAdder())   // 通用数据添加，只需要在所需要添加的接口的请求头上加上Cookie-Name:token,就会自动往当前请求的请求头上添加Token
                .level(DebugInterceptor.Level.BASIC)    // 网络数据调试等级
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
