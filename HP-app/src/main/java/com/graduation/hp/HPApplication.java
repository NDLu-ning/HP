package com.graduation.hp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.graduation.hp.core.app.base.App;
import com.graduation.hp.core.app.base.delegate.AppDelegate;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.utils.Preconditions;

/**
 * Created by Ning on 2018/11/15.
 */
public class HPApplication extends Application
        implements App {

    private static HPApplication sApplication;
    private AppDelegate mAppDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        if (mAppDelegate != null)
            this.mAppDelegate.onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }

    public static HPApplication getInstance() {
        return sApplication;
    }

    public static int getColorById(int colorResId) {
        return ContextCompat.getColor(getInstance(), colorResId);
    }

    public static String getStringById(int resId) {
        return getInstance().getString(resId);
    }

    public static String getStringById(int resId, Object... formatArgs) {
        return getInstance().getString(resId, formatArgs);
    }



}
