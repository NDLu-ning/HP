package com.graduation.hp.app.di.module;


import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.preferences.PreferencesHelper;
import com.graduation.hp.repository.preferences.PreferencesHelperImpl;

import dagger.Module;
import dagger.Provides;

/**
 * 提供项目依赖对象
 */
@Module
public abstract class GeneralModule {

    public GeneralModule() {
    }

    @Provides
    static PreferencesHelper providePreferencesHelper() {
        return PreferencesHelperImpl.getInstance();
    }

    @Provides
    static RepositoryHelper provideRepositoryHelper(PreferencesHelper preferencesHelper, HttpHelper httpHelper) {
        return new RepositoryHelper(preferencesHelper, httpHelper);
    }
}
