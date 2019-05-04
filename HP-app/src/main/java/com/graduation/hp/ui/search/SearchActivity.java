package com.graduation.hp.ui.search;

import android.support.v4.app.Fragment;

import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.presenter.SearchPresenter;
import com.graduation.hp.repository.contact.SearchContact;

public class SearchActivity extends SingleFragmentActivity<SearchPresenter>
        implements SearchContact.View {



    @Override
    protected Fragment createMainContentFragment() {
        return null;
    }
}
