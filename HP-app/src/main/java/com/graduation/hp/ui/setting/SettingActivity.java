package com.graduation.hp.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.event.TokenInvalidEvent;
import com.graduation.hp.core.app.listener.SimpleItemClickListenerAdapter;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.core.utils.DialogUtils;
import com.graduation.hp.presenter.SettingPresenter;
import com.graduation.hp.repository.contact.SettingContact;
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.ui.auth.AuthActivity;
import com.graduation.hp.ui.navigation.NavigationTabActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SettingActivity extends SingleFragmentActivity<SettingPresenter>
        implements SettingContact.View, SettingFragment.SettingFragmentListener {

    public static final String[] TEXTSIZE = {"小", "中", "大", "特大"};

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    protected Fragment createMainContentFragment() {
        return SettingFragment.newInstance();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getSupportFragmentManager().addOnBackStackChangedListener(this::setHomeButtonEnabled);
        initToolbar(mRootView, getString(R.string.tips_myself_setting), R.mipmap.ic_navigation_back_white, 0);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerActivityComponent.builder().appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onGetUserInfoSuccess(User user) {
        EventBus.getDefault().post(user);
    }

    @Override
    public void operateLocalTextSizeSuccess(int textSize) {
        EventBus.getDefault().post(textSize);
    }

    @Override
    public void onLogoutSuccess() {
        showMessage(getString(R.string.tips_logout_success));
        startActivity(NavigationTabActivity.createIntent(this, false));
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    @Override
    public void skipToLoginPage(TokenInvalidEvent event) {
        EventBus.getDefault().cancelEventDelivery(TokenInvalidEvent.class);
        runOnUiThread(() -> startActivity(AuthActivity.createIntent(this)));
    }

    private void setHomeButtonEnabled() {
        if (getMainContentFragment() instanceof SettingFragment || getSupportFragmentManager().getBackStackEntryCount() > 0) {
            setToolbarTitle(getString(R.string.tips_myself_setting));
        } else {
            setToolbarTitle(getString(R.string.tips_setting_about));
        }
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        finish();
    }

    @Override
    public void getCurrentUserInfo() {
        mPresenter.getCurrentUserInfo();
    }

    @Override
    public int getTextSize() {
        return mPresenter.getTextSize();
    }

    @Override
    public void getAppVersion() {
        mPresenter.onVersionControlClick();
    }

    @Override
    public void updateTextSize() {
        DialogUtils.showTextSortDialog(this, new SimpleItemClickListenerAdapter() {
            @Override
            public void OnItemClick(View view, int position) {
                mPresenter.updateTextSize(position);
            }
        });
    }

    @Override
    public void operateUserLayout(boolean isCurUserLogin) {
        if (!isCurUserLogin) {
            startActivity(AuthActivity.createIntent(this));
        } else {
            DialogUtils.showConfirmDialog(this, getString(R.string.tips_logout_subtitle), getString(R.string.tips_logout_message),
                    new SimpleItemClickListenerAdapter() {
                        @Override
                        public void OnItemClick(View view, int position) {
                            mPresenter.logout();
                        }
                    });
        }
    }


    @Override
    public void showAboutPage() {

    }
}
