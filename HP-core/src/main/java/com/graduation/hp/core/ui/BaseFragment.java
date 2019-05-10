package com.graduation.hp.core.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.graduation.hp.core.R;
import com.graduation.hp.core.app.base.lifecycleable.FragmentLifecycleable;
import com.graduation.hp.core.app.event.TokenInvalidEvent;
import com.graduation.hp.core.mvp.BaseContact;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.utils.DialogUtils;
import com.graduation.hp.core.utils.NetworkUtils;
import com.graduation.hp.core.utils.ToastUtils;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;

/**
 * 实现了MVP架构的Fragment
 * Created by Ning on 2019/4/24.
 */

public abstract class BaseFragment<P extends BasePresenter> extends BaseLazyLoadFragment
        implements BaseContact.View, FragmentLifecycleable, Toolbar.OnMenuItemClickListener {

    protected Context mContext;
    protected ProgressDialog mProgressDialog;

    @Inject
    @Nullable
    protected P mPresenter; // 处理业务请求以及控制页面显示

    private Toolbar mToolbar;
    private AppCompatTextView mToolbarTitle;
    private ImageButton mToolbarLeftBtn;
    private ImageButton mToolbarRightBtn;
    private AppCompatTextView mToolbarRightTv;

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    @Override
    protected void beforeInit(Bundle savedInstanceState, View view) {
        if (mPresenter != null) {
            mPresenter.onAttach(this);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mPresenter != null) {
            mPresenter.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        if (mPresenter != null && savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.show(getContext(), msg);
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
        mProgressDialog = DialogUtils.showLoadingDialog(mContext, message);
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
    public void onDestroyView() {
        if (mPresenter != null)
            mPresenter.onDetach();//释放资源
        this.mPresenter = null;
        super.onDestroyView();
    }

    public void onToolbarLeftClickListener(View v) {
    }

    public void onToolbarRightClickListener(View v) {
    }

    public boolean isNetworkAvailable() {
        return NetworkUtils.isConnected(mContext);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public boolean useEventBus() {
        return false;
    }


    private void initToolbar(View rootView) {
        mToolbarLeftBtn = rootView.findViewById(R.id.toolbar_left_btn);
        mToolbarRightBtn = rootView.findViewById(R.id.toolbar_right_btn);
        mToolbarRightTv = rootView.findViewById(R.id.toolbar_right_tv);
        mToolbarTitle = rootView.findViewById(R.id.toolbar_title);
        mToolbar = rootView.findViewById(R.id.toolbar);
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

    protected int getMenuId() {
        return 0;
    }
}
