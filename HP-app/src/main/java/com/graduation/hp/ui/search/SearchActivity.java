package com.graduation.hp.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.presenter.SearchPresenter;
import com.graduation.hp.repository.contact.SearchContact;

public class SearchActivity extends SingleFragmentActivity<SearchPresenter>
        implements SearchContact.View {

    public static Intent createIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    @Override
    protected Fragment createMainContentFragment() {
        return SearchFragment.newInstance("");
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

    @Override
    public void getSearchKeywords() {
        mPresenter.getSearchRecords();
    }

    @Override
    public void saveSearchKeyword(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            showMessage(getString(R.string.tips_keywords_not_empty));
            return;
        }
        mPresenter.saveSearchRecord(keyword);
    }

    @Override
    public void startSearchResultPage(String keyword) {
        replaceMainContentFragment(SearchResultFragment.newInstance(keyword), true);
    }

    @Override
    public void startSearchPage(String keyword) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
        replaceMainContentFragment(SearchFragment.newInstance(keyword), true);
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
            overridePendingTransition(0, R.anim.slide_out_to_right);
        }
    }
}
