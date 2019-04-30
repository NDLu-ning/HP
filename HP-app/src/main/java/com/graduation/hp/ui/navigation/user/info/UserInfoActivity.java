package com.graduation.hp.ui.navigation.user.info;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.presenter.UserInfoPresenter;
import com.graduation.hp.repository.contact.UserInfoContact;

public class UserInfoActivity extends SingleFragmentActivity<UserInfoPresenter>
        implements UserInfoContact.View {

    public static Intent createIntent(Context context) {
        return new Intent(context, UserInfoActivity.class);
    }

    @Override
    protected Fragment createMainContentFragment() {
        return UserInfoFragment.newInstance();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }
}
