package com.graduation.hp.core.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.hp.core.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.core.widget.ViewStateManager;


/**
 * 实现MVP框架，且能根据请求结果切换显示状态的Fragment
 * Created by Ning on 2018/11/15.
 */

public abstract class RootFragment<P extends BasePresenter>
        extends BaseFragment<P> {
    private static final int ID_LOADING = R.id.view_loading;
    private static final int ID_ERROR = R.id.view_error;
    private static final int ID_EMPTY = R.id.view_empty;
    private static final int ID_MAIN = R.id.view_main;

    private static final int ID_ERROR_RETRY_BUTTON = R.id.error_retry;
    private static final int ID_EMPTY_TEXT = R.id.empty_retry;
    private static final int ID_EMPTY_IMAGE = R.id.empty_iv;

    private ViewStateManager mViewStateManager;

    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        if (rootView == null) {
            throw new IllegalStateException(
                    "RootActivity must implements the getLayoutId() method and do not return 0");
        }
        ViewGroup mViewMain = rootView.findViewById(ID_MAIN);
        if (mViewMain == null) {
            throw new IllegalStateException(
                    "The subclass of RootActivity must contain a View named 'view_main'.");
        }
        mViewStateManager = getViewStateManager(rootView);
        final Button networkErrorRetryButton = rootView.findViewById(ID_ERROR_RETRY_BUTTON);
        networkErrorRetryButton.setOnClickListener(v -> onRetryClick());
        if (shouldShowNoDataView()) {
            setupNoDataView(rootView);
        }
    }

    /**
     * 显示主页面
     */
    @Override
    public void showMain() {
        mViewStateManager.show(ID_MAIN);
        Log.d("TAG", "visible:" + (mRootView.findViewById(R.id.view_main).getVisibility() == View.VISIBLE));
    }

    /**
     * 显示加载页面
     */
    @Override
    public void showLoading() {
        mViewStateManager.show(ID_LOADING);
    }

    // 显示请求错误页面
    @Override
    public void showError(String msg) {
        mViewStateManager.show(ID_ERROR);
    }


    @Override
    public void showEmpty() {
        mViewStateManager.show(ID_EMPTY);
    }

    protected void onRetryClick() {
    }

    protected abstract boolean shouldShowNoDataView();

    protected abstract int getNoDataStringResId();
    // Override this if you need a different no data view, but name the root id R.id.no_data_view

    protected void setupNoDataView(View layout) {
        final TextView noDataMsgTextView = layout.findViewById(ID_EMPTY_TEXT);
        if (noDataMsgTextView == null) {
            throw new InflateException("The id of TextView in EmptyView must be \"empty_retry\"");
        }
        final ImageView noDataMsgImageView = layout.findViewById(ID_EMPTY_IMAGE);
        if (noDataMsgImageView == null) {
            throw new InflateException("The id of ImageView in EmptyView must be \"empty_iv\"");
        }
        noDataMsgTextView.setText(getNoDataStringResId());
        if (getNoDataDrawableResId() != 0) {
            noDataMsgImageView.setImageResource(getNoDataDrawableResId());
        }
        noDataMsgTextView.setOnClickListener(v -> onEmptyClick());

    }

    protected void onEmptyClick() {

    }

    protected int getNoDataDrawableResId() {
        return 0;
    }

    private ViewStateManager getViewStateManager(View layout) {
        if (shouldShowNoDataView()) {
            return new ViewStateManager(layout, ID_LOADING, ID_ERROR, ID_MAIN,
                    ID_EMPTY);
        } else {
            return new ViewStateManager(layout, ID_LOADING, ID_ERROR, ID_MAIN);
        }
    }
}
