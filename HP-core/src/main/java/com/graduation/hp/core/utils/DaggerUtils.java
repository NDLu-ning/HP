package com.graduation.hp.core.utils;

import android.content.Context;

import com.graduation.hp.core.app.base.App;
import com.graduation.hp.core.app.di.component.AppComponent;


/**
 * Created by Ning on 2018/11/18.
 */

public class DaggerUtils {
    private DaggerUtils() {
    }


    public static AppComponent obtainAppComponentFromContext(Context context) {
        return ((App) context.getApplicationContext()).getAppComponent();
    }
}
