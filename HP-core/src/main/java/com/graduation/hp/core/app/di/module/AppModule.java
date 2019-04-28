package com.graduation.hp.core.app.di.module;

import android.app.Application;
import android.support.v4.app.FragmentManager;

import com.graduation.hp.core.app.base.lifecycleable.ActivityLifecycle;
import com.graduation.hp.core.app.base.lifecycleable.ActivityLifecycleForRxLifecycle;
import com.graduation.hp.core.app.base.lifecycleable.FragmentLifecycleForRxLifecycle;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.HttpHelperImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Ning on 2018/11/19.
 */

@Module
public abstract class AppModule {

    @Singleton
    @Binds
    abstract HttpHelper provideHttpHelper(HttpHelperImpl httpHelper);

    @Binds
    @Named("ActivityLifecycle")
    abstract Application.ActivityLifecycleCallbacks provideActivityLifecycle(ActivityLifecycle activityLifecycle);

    @Binds
    @Named("ActivityLifecycleForRxLifecycle")
    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycleForRxLifecycle(ActivityLifecycleForRxLifecycle activityLifecycleForRxLifecycle);

    @Provides
    @Singleton
    static FragmentManager.FragmentLifecycleCallbacks provideFragmentLifecycleForRxLifecycle() {
        return new FragmentLifecycleForRxLifecycle();
    }

    @Provides
    static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifecycles() {
        return new ArrayList<>();
    }

}