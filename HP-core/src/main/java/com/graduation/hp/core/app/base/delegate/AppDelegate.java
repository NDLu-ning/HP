package com.graduation.hp.core.app.base.delegate;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.graduation.hp.core.app.base.App;
import com.graduation.hp.core.app.base.lifecycleable.AppLifecycle;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.di.component.DaggerAppComponent;
import com.graduation.hp.core.app.di.module.ConfigModule;
import com.graduation.hp.core.app.di.module.GlobalConfigModule;
import com.graduation.hp.core.utils.ManifestParser;
import com.graduation.hp.core.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Application 生命周期代理类
 * Created by Ning on 2019/2/5.
 */

public class AppDelegate implements App, AppLifecycle {

    private Application mApplication;
    protected AppComponent mAppComponent;

    @Inject
    @Named("ActivityLifecycle")
    protected Application.ActivityLifecycleCallbacks mActivityLifecycle;
    @Inject
    @Named("ActivityLifecycleForRxLifecycle")
    protected Application.ActivityLifecycleCallbacks mActivityLifecycleForRxLifecycle;

    private List<ConfigModule> mModules;
    private List<AppLifecycle> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();

    public AppDelegate(@NonNull Context base) {
        //用反射, 将 AndroidManifest.xml 中带有 ConfigModule 标签的 class 转成对象集合（List<ConfigModule>）
        this.mModules = new ManifestParser(base).parse();
        //遍历之前获得的集合, 执行每一个 ConfigModule 实现类的某些方法
        for (ConfigModule module : mModules) {
            //将框架外部, 开发者实现的 Application 的生命周期回调 (AppLifecycles) 存入 mAppLifecycles 集合 (此时还未注册回调)
            module.injectAppLifecycle(base, mAppLifecycles);
            //将框架外部, 开发者实现的 Activity 的生命周期回调 (ActivityLifecycleCallbacks) 存入 mActivityLifecycles 集合 (此时还未注册回调)
            module.injectActivityLifecycle(base, mActivityLifecycles);
        }
    }

    /**
     * 应用开启的第一个配置的生命周期
     * @param base
     */
    @Override
    public void attachBaseContext(@NonNull Context base) {
        //遍历 mAppLifecycles, 执行所有已注册的 AppLifecycles 的 attachBaseContext() 方法 (框架外部, 开发者扩展的逻辑)
        for (AppLifecycle lifecycle : mAppLifecycles) {
            lifecycle.attachBaseContext(base);
        }
    }

    /**
     *
     * @param application
     */
    @Override
    public void onCreate(@NonNull Application application) {
        this.mApplication = application;
        mAppComponent = DaggerAppComponent
                .builder()
                .application(mApplication)//提供application
                .globalConfigModule(getGlobalConfigModule(mApplication, mModules))//全局配置
                .build();
        mAppComponent.inject(this);
        this.mModules = null;
        //注册已实现的 Activity 生命周期逻辑
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);
        //注册已实现的 RxLifecycle 生命周期逻辑
        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle);
        for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(lifecycle);
        }
        //执行框架外部, 开发者扩展的 App onCreate 逻辑
        for (AppLifecycle lifecycle : mAppLifecycles) {
            lifecycle.onCreate(mApplication);
        }
    }

    /**
     * 应用终止 注销所配置的接口以及实体类
     * @param application
     */
    @Override
    public void onTerminate(@NonNull Application application) {
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mActivityLifecycleForRxLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycleForRxLifecycle);
        }
        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks callbacks : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(callbacks);
            }
        }
        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (AppLifecycle lifecycle : mAppLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }
        this.mAppComponent = null;
        this.mActivityLifecycle = null;
        this.mActivityLifecycleForRxLifecycle = null;
        this.mActivityLifecycles = null;
        this.mAppLifecycles = null;
        this.mApplication = null;
    }

    private GlobalConfigModule getGlobalConfigModule(Application application, List<ConfigModule> modules) {
        GlobalConfigModule.Builder builder = GlobalConfigModule
                .builder();
        //遍历 ConfigModule 集合, 给全局配置 GlobalConfigModule 添加参数
        for (ConfigModule module : modules) {
            module.applyOptions(application, builder);
        }
        return builder.build();
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppComponent,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                AppComponent.class.getName(), getClass().getName(), Application.class.getName());
        return mAppComponent;
    }
}
