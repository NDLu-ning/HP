package com.graduation.hp.core.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.hp.core.app.base.IFragment;
import com.graduation.hp.core.app.base.lifecycleable.FragmentLifecycleable;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.utils.DaggerUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Ning on 2018/11/26.
 */

public abstract class BaseLazyLoadFragment extends Fragment
        implements IFragment, FragmentLifecycleable {


    private BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();

    protected View mRootView;
    protected Unbinder mUnBinder;

    private boolean fragmentVisible;
    private boolean reuseView;
    private boolean firstVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mRootView == null) {
            return;
        }
        // 保证第一次加载进入且可见状态下调用 onLazyLoad 方法
        if (firstVisible && isVisibleToUser) {
            onLazyLoad();
            firstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            fragmentVisible = true;
            return;
        }
        if (fragmentVisible) {
            onFragmentVisibleChange(false);
            fragmentVisible = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        initVariable();
        // 注入所需参数
        setupFragmentComponent(DaggerUtils.obtainAppComponentFromContext(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mUnBinder == null) {
            mUnBinder = ButterKnife.bind(this, view);
        }
        beforeInit(savedInstanceState,view);
        init(savedInstanceState, view);
        if (mRootView == null) {
            mRootView = view;
            if (getUserVisibleHint()) {
                if (firstVisible) {
                    onLazyLoad();
                    firstVisible = false;
                }
                onFragmentVisibleChange(true);
                fragmentVisible = true;
            }
        }
        super.onViewCreated(reuseView ? mRootView : view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
        mUnBinder = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        initVariable();
        super.onDestroy();
    }


    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    protected void beforeInit(Bundle savedInstanceState, View view) {
    }

    /**
     * 懒加载数据
     *
     */
    protected void onLazyLoad() {
    }

    /**
     * 当前Fragment是否处于显示状态
     * @param isFragmentVisible
     */
    protected void onFragmentVisibleChange(boolean isFragmentVisible) {
    }

    private void initVariable() {
        firstVisible = true;
        fragmentVisible = false;
        mRootView = null;
        reuseView = true;
    }

    protected abstract void init(Bundle savedInstanceState, View view);


    protected abstract int getLayoutId();

}
