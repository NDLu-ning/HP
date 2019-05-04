package com.graduation.hp.ui.navigation.news.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.event.TokenInvalidEvent;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.presenter.NewsDetailPresenter;
import com.graduation.hp.repository.contact.NewsDetailContact;
import com.graduation.hp.ui.auth.AuthActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NewsDetailActivity extends SingleFragmentActivity {

    public static Intent createIntent(Context context, long newsId) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(Key.NEWS_ID, newsId);
        return intent;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        findViewById(R.id.toolbar).setVisibility(View.GONE);
    }

    @Override
    protected Fragment createMainContentFragment() {
        Intent intent = getIntent();
        long newsId = intent.getLongExtra(Key.NEWS_ID, 0L);
        return NewsDetailFragment.newInstance(newsId);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED, priority = 2)
    @Override
    public void skipToLoginPage(TokenInvalidEvent event) {
        startActivity(AuthActivity.createIntent(this));
        EventBus.getDefault().cancelEventDelivery(event);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }
}
