package com.graduation.hp.core.utils;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static android.arch.lifecycle.Lifecycle.State.RESUMED;

public final class LifecycleUtil {
    private LifecycleUtil() { }

    public static void runOnResume(FragmentActivity activity, Runnable runnable) {
        Lifecycle lifecycle = activity.getLifecycle();
        StateChecker checker = () ->
                lifecycle.getCurrentState() == RESUMED && !activity.getSupportFragmentManager().isStateSaved();
        runNowOrNextResume(checker, lifecycle, runnable);
    }

    public static void runOnResume(Fragment fragment, Runnable runnable) {
        Lifecycle lifecycle = fragment.getLifecycle();
        StateChecker checker = () ->
                lifecycle.getCurrentState() == RESUMED && !fragment.getChildFragmentManager().isStateSaved();
        runNowOrNextResume(checker, lifecycle, runnable);
    }

    public static void commitTransactionSafely(FragmentActivity activity, TransactionBuilder transactionBuilder) {
        Lifecycle lifecycle = activity.getLifecycle();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        commitTransactionSafely(lifecycle, fragmentManager, transactionBuilder);
    }

    public static void commitTransactionSafely(Fragment fragment, TransactionBuilder transactionBuilder) {
        Lifecycle lifecycle = fragment.getLifecycle();
        FragmentManager fragmentManager = fragment.getChildFragmentManager();
        commitTransactionSafely(lifecycle, fragmentManager, transactionBuilder);
    }

    /**
     * Transaction must be committed before onSaveInstanceState is called.
     */
    private static void commitTransactionSafely(Lifecycle lifecycle, FragmentManager fragmentManager,
                                                TransactionBuilder transactionBuilder) {
        StateChecker checker = () ->
                !fragmentManager.isStateSaved() && !fragmentManager.isDestroyed();
        @SuppressLint("CommitTransaction")
        Runnable runnable = () ->
                transactionBuilder.createTransaction(fragmentManager.beginTransaction()).commit();
        runNowOrNextResume(checker, lifecycle, runnable);
    }

    private static void runNowOrNextResume(StateChecker checker, Lifecycle lifecycle, Runnable runnable) {
        if (checker.isCorrect()) {
            runnable.run();
        } else {
            LifecycleObserver observer = new LifecycleObserver() {
                @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                void onResume() {
                    runnable.run();
                    lifecycle.removeObserver(this);
                }

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                void onDestroy() {
                    lifecycle.removeObserver(this);
                }
            };
            lifecycle.addObserver(observer);
        }
    }

    private interface StateChecker {
        boolean isCorrect();
    }

    public interface TransactionBuilder {
        FragmentTransaction createTransaction(FragmentTransaction transaction);
    }
}
