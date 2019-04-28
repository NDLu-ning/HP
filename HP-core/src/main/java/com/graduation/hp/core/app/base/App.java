package com.graduation.hp.core.app.base;

import android.support.annotation.NonNull;

import com.graduation.hp.core.app.di.component.AppComponent;


public interface App {
    @NonNull
    AppComponent getAppComponent();
}