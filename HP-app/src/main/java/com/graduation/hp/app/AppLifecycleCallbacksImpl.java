package com.graduation.hp.app;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.graduation.hp.core.app.base.lifecycleable.AppLifecycle;
import com.graduation.hp.core.utils.GlideUtils;
import com.lzy.ninegrid.NineGridView;

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
        NineGridView.setImageLoader(new NineGridView.ImageLoader() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String url) {
                GlideUtils.loadImage(imageView, url);
            }

            @Override
            public Bitmap getCacheImage(String url) {
                return null;
            }
        });
    }

    @Override
    public void onCreate(@NonNull Application application) {

    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
