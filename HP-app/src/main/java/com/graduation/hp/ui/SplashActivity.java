package com.graduation.hp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseActivity;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.presenter.SplashPresenter;
import com.graduation.hp.repository.contact.SplashContact;
import com.graduation.hp.ui.navigation.NavigationTabActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import butterknife.BindView;

/**
 * 开屏页面
 */
public class SplashActivity extends BaseActivity<SplashPresenter>
        implements SplashContact.View {

    @BindView(R.id.splash_skip_btn)
    Button splashSkipBtn;

    private Timer mTimer = new Timer();
    private final DelayCloseTimerTask mTimerTask = new DelayCloseTimerTask(this);

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        splashSkipBtn.setOnClickListener(v -> checkUserStatus());
        mTimer.schedule(mTimerTask, 0L, 1000L);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    public void checkUserStatus() {
        clearTimer();
        mPresenter.checkUserLoginStatus();
    }

    @Override
    public void goToNextPage(boolean isCurUserLogin) {
        LogUtils.d("isCurUserLogin:" + isCurUserLogin);
        startActivity(NavigationTabActivity.createIntent(this, isCurUserLogin));
        finish();
    }

    private void clearTimer() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = null;
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
    }

    private class DelayCloseTimerTask extends TimerTask {

        private final WeakReference<SplashActivity> mActivity;
        private CountDownLatch latch = new CountDownLatch(3);


        public DelayCloseTimerTask(SplashActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            SplashActivity activity = mActivity.get();
            if (activity != null) {
                activity.runOnUiThread(() -> {
                    if (latch.getCount() <= 0) {
                        activity.checkUserStatus();
                    } else {
                        activity.splashSkipBtn.setText(HPApplication.getStringById(R.string.template_skip, latch.getCount()));
                        latch.countDown();
                    }
                });
            }
        }
    }
}
