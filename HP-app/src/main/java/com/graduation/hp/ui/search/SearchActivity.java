package com.graduation.hp.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.graduation.hp.R;
import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.presenter.SearchPresenter;
import com.graduation.hp.repository.contact.SearchContact;

public class SearchActivity extends SingleFragmentActivity<SearchPresenter>
        implements SearchContact.View {

    @Override
    protected Fragment createMainContentFragment() {
        return SearchFragment.newInstance();
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_search;
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
