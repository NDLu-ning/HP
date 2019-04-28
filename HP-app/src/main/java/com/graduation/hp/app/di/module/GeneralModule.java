package com.graduation.hp.app.di.module;


import com.graduation.hp.core.app.di.scope.FragmentScope;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.preferences.PreferencesHelper;
import com.graduation.hp.repository.preferences.PreferencesHelperImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

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
