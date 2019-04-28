package com.graduation.hp.core.app.di.component;

import android.app.Application;

import com.graduation.hp.core.app.base.delegate.AppDelegate;
import com.graduation.hp.core.app.di.module.AppModule;
import com.graduation.hp.core.app.di.module.ClientModule;
import com.graduation.hp.core.app.di.module.GlobalConfigModule;
import com.graduation.hp.core.repository.http.HttpHelper;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Ning on 2018/11/18.
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, GlobalConfigModule.class})
public interface AppComponent {
    Application application();

    HttpHelper httpHelper();

    void inject(AppDelegate application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder globalConfigModule(GlobalConfigModule globalConfigModule);

        AppComponent build();
    }
}
