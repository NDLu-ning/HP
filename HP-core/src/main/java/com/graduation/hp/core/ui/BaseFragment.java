package com.graduation.hp.core.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.hp.core.R;
import com.graduation.hp.core.app.base.IFragment;
import com.graduation.hp.core.app.base.lifecycleable.FragmentLifecycleable;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.mvp.BaseContact;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.utils.DaggerUtils;
import com.graduation.hp.core.utils.DialogUtils;
import com.graduation.hp.core.utils.NetworkUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Ning on 2019/4/24.
 */

public abstract class BaseFragment<P extends BasePresenter> extends BaseLazyLoadFragment
        implements BaseContact.View, FragmentLifecycleable, Toolbar.OnMenuItemClickListener {

    protected Context mContext;
    protected ProgressDialog mProgressDialog;

    @Inject
    @Nullable
    protected P mPresenter; // 处理业务请求以及控制页面显示

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
    public void showMessage(String msg) {
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
    public void skipToLoginPage() {

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

    protected void initToolbar(View rootView, String title, @DrawableRes int leftDrawable) {
        initToolbar(rootView, title, leftDrawable, 0);
    }

    protected void initToolbar(View rootView, String title, @DrawableRes int leftDrawable, @DrawableRes int rightDrawable) {
        ImageButton tbLeftBtn = rootView.findViewById(R.id.toolbar_left_btn);
        ImageButton tbRightBtn = rootView.findViewById(R.id.toolbar_right_btn);
        TextView tbTitleTv = rootView.findViewById(R.id.toolbar_title);
        if (tbTitleTv != null && !TextUtils.isEmpty(title)) {
            tbTitleTv.setText(title);
        }
        if (tbLeftBtn != null && leftDrawable != 0) {
            tbLeftBtn.setImageResource(leftDrawable);
            tbLeftBtn.setOnClickListener(v -> onToolbarLeftClickListener(v));
        }
        if (tbRightBtn != null && rightDrawable != 0) {
            tbRightBtn.setImageResource(rightDrawable);
            tbRightBtn.setOnClickListener(v -> onToolbarRightClickListener(v));
        }
    }

    protected int getMenuId() {
        return 0;
    }
}
