package com.graduation.hp.ui.navigation.news.detail;

import android.support.v4.app.Fragment;

import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.presenter.NewsDetailPresenter;
import com.graduation.hp.repository.contact.NewsDetailContact;

public class NewsDetailActivity extends SingleFragmentActivity<NewsDetailPresenter>
        implements NewsDetailContact.View {



    @Override
    protected Fragment createMainContentFragment() {
        return null;
    }
}
