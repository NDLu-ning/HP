package com.graduation.hp.core.widget;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.graduation.hp.core.R;


/**
 * Use to display different views in different states
 * Created by Ning on 2019/4/25.
 */
public final class ViewStateManager {

    private final View mRootView;
    private final int[] mViewIds;
    private int mCurrentViewId;

    public ViewStateManager(View rootView, int... viewIds) {
        mRootView = rootView;
        mViewIds = viewIds;
    }

    public void show(int viewId) {
        if (mViewIds == null) {
            return;
        }
        if (viewId == mCurrentViewId) {
            return;
        }
        // Make sure to hide all Views first so that we don't have two Views possibly
        // showing at the same time.
        hideAll();
        final View v = mRootView.findViewById(viewId);
        show(v);
        mCurrentViewId = viewId;
    }

    /**
     * Set all Views managed by this ViewStateManager to be {@link View#GONE}.
     */
    public void hideAll() {
        for (int viewId : mViewIds) {
            final View v = mRootView.findViewById(viewId);
            hide(v);
        }
    }

    /**
     * Set the specified View's visibility to {@link android.view.View#VISIBLE}.
     *
     * @param v the View to show
     */
    private void show(View v) {
        if (v == null) {
            return;
        }
        v.startAnimation(getFadeInAnimation(v.getContext()));
        v.setVisibility(View.VISIBLE);
    }

    /**
     * Set the specified View's visibility to {@link android.view.View#GONE}.
     *
     * @param v the View to hide
     */
    private void hide(View v) {
        if (v == null) {
            return;
        }

        v.setVisibility(View.GONE);
    }


    private Animation getFadeInAnimation(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }
}
