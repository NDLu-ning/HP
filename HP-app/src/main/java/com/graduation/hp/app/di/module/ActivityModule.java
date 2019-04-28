package com.graduation.hp.app.di.module;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.graduation.hp.core.app.di.scope.ActivityScope;
import com.graduation.hp.core.app.di.scope.FragmentScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;


@Module
public class ActivityModule {

    private final AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return this.mActivity;
    }

    @Provides
    @ActivityScope
    RxPermissions provideRxPermissions() {
        return new RxPermissions(mActivity);
    }

    @Provides
    @ActivityScope
    static Items provideItems() {
        return new Items();
    }

    @Provides
    @ActivityScope
    static MultiTypeAdapter provideMultiTypeAdapter(Items items) {
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
        return adapter;
    }

}
