package com.graduation.hp.core.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.graduation.hp.core.R;
import com.graduation.hp.core.app.base.IActivity;
import com.graduation.hp.core.app.base.lifecycleable.ActivityLifecycleable;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.event.TokenInvalidEvent;
import com.graduation.hp.core.app.listener.PermissionCallback;
import com.graduation.hp.core.mvp.BaseContact;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.utils.DaggerUtils;
import com.graduation.hp.core.utils.DialogUtils;
import com.graduation.hp.core.utils.NetworkUtils;
import com.graduation.hp.core.utils.ToastUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * 实现了MVP框架的基础Activity
 * 可适用不需要MVP架构的简单Activity
 *
 * @param <P>
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
        implements BaseContact.View, ActivityLifecycleable, IActivity, Toolbar.OnMenuItemClickListener {

    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();

    protected View mRootView;
    private Unbinder mUnbinder;

    protected ProgressDialog mProgressDialog;

    private Toolbar mToolbar;
    private AppCompatTextView mToolbarTitle;
    private ImageButton mToolbarLeftBtn;
    private ImageButton mToolbarRightBtn;
    private AppCompatTextView mToolbarRightTv;

    private PermissionCallback mPermissionCallback;

    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = getLayoutId(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                mRootView = LayoutInflater.from(this).inflate(layoutResID, null);
                setContentView(mRootView);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
                setupActivityComponent(DaggerUtils.obtainAppComponentFromContext(this));
                if (mPresenter != null) {
                    mPresenter.onAttach(this);
                }
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (mPresenter != null)
            mPresenter.onDetach();//释放资源
        this.mPresenter = null;
        mRootView = null;
        super.onDestroy();
    }

    public void setPermissionCallback(PermissionCallback mPermissionCallback) {
        this.mPermissionCallback = mPermissionCallback;
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.show(this, msg);
    }

    @Override
    public void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void showDialog(String message) {
        dismissDialog();
        mProgressDialog = DialogUtils.showLoadingDialog(this, message);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showMain() {

    }

    @Override
    public void skipToLoginPage(TokenInvalidEvent event) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public boolean useFragment() {
        return false;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    private void initToolbar(View rootView) {
        mToolbarLeftBtn = rootView.findViewById(R.id.toolbar_left_btn);
        mToolbarRightBtn = rootView.findViewById(R.id.toolbar_right_btn);
        mToolbarTitle = rootView.findViewById(R.id.toolbar_title);
        mToolbar = rootView.findViewById(R.id.toolbar);
        mToolbarRightTv = rootView.findViewById(R.id.toolbar_right_tv);
        if (mToolbarLeftBtn != null) {
            mToolbarLeftBtn.setOnClickListener(this::onToolbarLeftClickListener);
        }
        if (mToolbarRightBtn != null) {
            mToolbarRightBtn.setOnClickListener(this::onToolbarRightClickListener);
        }
        if (mToolbarRightTv != null) {
            mToolbarRightTv.setOnClickListener(this::onToolbarRightClickListener);
        }
    }

    protected void initToolbar(View rootView, String title, @DrawableRes int leftDrawable) {
        initToolbar(rootView, title, leftDrawable, 0);
    }

    protected void initToolbar(View rootView, String title, @DrawableRes int leftDrawable, @DrawableRes int rightDrawable) {
        initToolbar(rootView, title, leftDrawable, rightDrawable, 0);
    }

    protected void initToolbar(View rootView, String title, @DrawableRes int leftDrawable, String rightText, @ColorRes int rightTextColor) {
        initToolbar(rootView, title, leftDrawable, rightText, rightTextColor, 0);
    }

    protected void initToolbar(View rootView, String title, @DrawableRes int leftDrawable, @DrawableRes int rightDrawable, @DrawableRes int backgroundRes) {
        initToolbar(rootView);
        setToolbarTitle(title);
        setToolbarLeftDrawableRes(leftDrawable);
        setToolbarRightDrawableRes(rightDrawable);
        setToolbarBackgroundRes(backgroundRes);
    }

    protected void initToolbar(View rootView, String title, @DrawableRes int leftDrawable, String rightText, @ColorRes int rightTextColor, @DrawableRes int backgroundRes) {
        initToolbar(rootView);
        setToolbarTitle(title);
        setToolbarLeftDrawableRes(leftDrawable);
        setToolbarRightTvText(rightText);
        setToolbarRightTvTextColor(rightTextColor);
        setToolbarBackgroundRes(backgroundRes);
    }

    protected void setToolbarTitle(String title) {
        if (mToolbarTitle != null && !TextUtils.isEmpty(title)) {
            mToolbarTitle.setText(title);
        }
    }

    protected void setToolbarBackgroundRes(@DrawableRes int backgroundRes) {
        if (mToolbar != null && backgroundRes != 0) {
            mToolbar.setBackgroundResource(backgroundRes);
        }
    }

    protected void setToolbarRightTvText(String rightText) {
        if (mToolbarRightTv != null && !TextUtils.isEmpty(rightText)) {
            mToolbarRightTv.setText(rightText);
        }
    }

    protected void setToolbarRightTvTextColor(@ColorRes int rightTextColor) {
        if (mToolbarRightTv != null && rightTextColor != 0) {
            mToolbarRightTv.setTextColor(getResources().getColor(rightTextColor));
        }
    }

    protected void setToolbarRightDrawableRes(@DrawableRes int rightDrawable) {
        if (mToolbarRightBtn != null && rightDrawable != 0) {
            mToolbarRightBtn.setImageResource(rightDrawable);
        }
    }

    protected void setToolbarLeftDrawableRes(@DrawableRes int leftDrawable) {
        if (mToolbarLeftBtn != null) {
            if (leftDrawable == 0) {
                mToolbarLeftBtn.setImageDrawable(null);
            } else {
                mToolbarLeftBtn.setImageResource(leftDrawable);
            }
        }
    }


    public void onToolbarLeftClickListener(View v) {
    }

    public void onToolbarRightClickListener(View v) {
    }

    public boolean isNetworkAvailable() {
        return NetworkUtils.isConnected(this);
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    protected abstract int getLayoutId(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);
}
