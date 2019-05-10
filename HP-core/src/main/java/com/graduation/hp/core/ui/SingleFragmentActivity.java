package com.graduation.hp.core.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.graduation.hp.core.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.utils.LifecycleUtil;

/**
 * 单Fragment的Activity，用于实现Fragment的进栈出栈
 *
 * @param <P>
 */
public abstract class SingleFragmentActivity<P extends BasePresenter> extends BaseActivity<P> {

    private static final int LAYOUT_ID = R.layout.activity_single_fragment;
    public static final int CONTENT_FRAME_ID = R.id.content_frame;

    @Override
    protected void initData(Bundle savedInstanceState) {
        initMainContentFragment();
    }

    private void initMainContentFragment() {
        final Fragment fragment = findFragmentById(CONTENT_FRAME_ID);
        if (fragment == null) {
            addFragment(CONTENT_FRAME_ID, createMainContentFragment());
        }
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return LAYOUT_ID;
    }


    protected void replaceMainContentFragment(Fragment fragment, boolean shouldAddToBackStack) {
        if (shouldAddToBackStack) {
            replaceFragmentAddToBackStack(CONTENT_FRAME_ID, fragment);
        } else {
            replaceFragment(CONTENT_FRAME_ID, fragment);
        }
    }

    protected void replaceMainContentFragment(Fragment fragment) {
        replaceMainContentFragment(fragment, false);
    }

    protected void replaceMainContentFragmentClearBackStack(Fragment fragment) {
        replaceFragmentClearBackStack(CONTENT_FRAME_ID, fragment);
    }

    protected Fragment getMainContentFragment() {
        return findFragmentById(CONTENT_FRAME_ID);
    }

    protected abstract Fragment createMainContentFragment();


    protected void replaceFragmentClearBackStack(int containerViewId, Fragment fragment) {
        final FragmentManager fragmentMgr = getSupportFragmentManager();

        // Documentation says that passing null as the first parameter for popBackStack()
        // will only pop the top state. Diane hackborne says that it's wrong and should
        // pop the entire back stack.
        // https://groups.google.com/forum/#!topic/android-developers/0qXCA9rW7EI
        fragmentMgr.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        replaceFragment(containerViewId, fragment);
    }

    public Fragment findFragmentById(int id) {
        return getSupportFragmentManager().findFragmentById(id);
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        LifecycleUtil.commitTransactionSafely(this, transaction ->
                transaction.add(containerViewId, fragment));
    }

    protected void replaceFragmentAddToBackStack(int containerViewId, Fragment fragment) {
        LifecycleUtil.commitTransactionSafely(this, new LifecycleUtil.TransactionBuilder() {
            @Override
            public FragmentTransaction createTransaction(FragmentTransaction transaction) {
                return transaction.replace(containerViewId, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null);
            }
        });
    }

    protected void replaceFragment(int containerViewId, Fragment fragment) {
        LifecycleUtil.commitTransactionSafely(this, transaction -> transaction
                .replace(containerViewId, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE));
    }
}
