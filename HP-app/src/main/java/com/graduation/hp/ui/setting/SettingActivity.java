package com.graduation.hp.ui.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseActivity;

public class SettingActivity extends BaseActivity {



    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        super.setupActivityComponent(appComponent);
    }
}
