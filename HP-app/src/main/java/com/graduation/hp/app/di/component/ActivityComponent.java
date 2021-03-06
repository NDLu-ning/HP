package com.graduation.hp.app.di.component;

import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.app.di.module.GeneralModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.di.scope.ActivityScope;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.ui.SplashActivity;
import com.graduation.hp.ui.auth.AuthActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Component;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {GeneralModule.class, ActivityModule.class})
public interface ActivityComponent {

    RepositoryHelper repositoryHelper();

    Items items();

    MultiTypeAdapter adapter();

    RxPermissions rxPermission();

    void inject(SplashActivity splashActivity);

    void inject(AuthActivity authActivity);
}
