package com.graduation.hp.ui.question;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.SingleFragmentActivity;

public class QuestionActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createMainContentFragment() {
        return null;
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
