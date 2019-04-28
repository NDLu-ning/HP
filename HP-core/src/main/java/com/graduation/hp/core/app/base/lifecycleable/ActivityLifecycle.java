package com.graduation.hp.core.app.base.lifecycleable;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.graduation.hp.core.app.base.IActivity;
import com.graduation.hp.core.utils.DaggerUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;

/**
 * Created by Ning on 2018/11/21.
 */

@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    Lazy<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycle;

    Lazy<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecycles;

    @Inject
    public ActivityLifecycle(Lazy<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycle,
                             Lazy<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecycles) {
        this.mFragmentLifecycle = mFragmentLifecycle;
        this.mFragmentLifecycles = mFragmentLifecycles;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (activity instanceof IActivity) {
            //如果要使用 EventBus 请将此方法返回 true
            IActivity iActivity = (IActivity) activity;
            if (iActivity.useEventBus()) {
                //注册到事件主线
                EventBus.getDefault().register(activity);
            }
            //这里提供 AppComponent 对象给 BaseActivity 的子类, 用于 Dagger2 的依赖注入
            iActivity.setupActivityComponent(DaggerUtils.obtainAppComponentFromContext(activity));
        }
        boolean useFragment = !(activity instanceof IActivity) || ((IActivity) activity).useFragment();
        if (activity instanceof FragmentActivity && useFragment) {
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle.get(), true);
            //注册框架外部, 开发者扩展的 Fragment 生命周期逻辑
            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycles.get()) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof IActivity) {
            IActivity iActivity = (IActivity) activity;
            if (iActivity.useEventBus())
                EventBus.getDefault().unregister(activity);
            iActivity = null;
            activity = null;
        }

    }
}
