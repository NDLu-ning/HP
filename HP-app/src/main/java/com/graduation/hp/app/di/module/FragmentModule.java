package com.graduation.hp.app.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.graduation.hp.core.app.di.scope.FragmentScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

@Module
public class FragmentModule {

    private final Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @FragmentScope
    Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @FragmentScope
    RxPermissions provideRxPermissions() {
        return new RxPermissions(mFragment.getActivity());
    }


    @Provides
    @FragmentScope
    static Items provideItems() {
        return new Items();
    }

    @Provides
    @FragmentScope
    static MultiTypeAdapter provideMultiTypeAdapter(Items items) {
        MultiTypeAdapter adapter = new MultiTypeAdapter();
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
        return adapter;
    }

}
