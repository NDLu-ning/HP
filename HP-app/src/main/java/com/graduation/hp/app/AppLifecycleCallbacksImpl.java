package com.graduation.hp.app;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import com.graduation.hp.core.app.base.lifecycleable.AppLifecycle;

/**
 * Created by Ning on 2018/11/21.
 */

public class AppLifecycleCallbacksImpl implements AppLifecycle {

    @Override
    public void attachBaseContext(@NonNull Context base) {
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            builder.detectFileUriExposure();
//        }
//        MultiDex.install(base);
    }

    @Override
    public void onCreate(@NonNull Application application) {

    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
