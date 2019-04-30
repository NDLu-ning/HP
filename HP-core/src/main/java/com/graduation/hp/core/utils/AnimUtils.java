package com.graduation.hp.core.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Ning on 2019/2/22.
 */

public class AnimUtils {

    public static void setScaleAnimation(View view, long duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.8f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.8f, 1.2f, 1f);
        scaleX.setDuration(duration);
        scaleY.setDuration(duration);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.start();
    }


    public static void rotate(View itemView, float startAngle, float endAngle, long duration) {
        itemView.animate().rotationBy(endAngle - startAngle).setDuration(duration)
                .start();
    }

    public static void scale(View target, float endScale, long duration) {
        target.animate().scaleX(endScale).scaleY(endScale).setDuration(duration)
                .start();
    }


    public static void alpha(View target, float startAlpha, float endAlpha, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "alpha", startAlpha, endAlpha)
                .setDuration(duration);
        animator.start();
    }

    public static void alphaBy(View target, float endAlpha, long duration) {
        target.animate().alphaBy(endAlpha).setDuration(duration)
                .start();
    }

    public static void translationXBy(View target, int start, int end, long duration) {
        target.animate().translationXBy(end - start).setDuration(duration)
                .start();
    }

    public static void translationY(View target, int start, int end, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "translationY", start, end).setDuration(duration);
        animator.start();
    }

    public static void translationY2(View target, int start, int end, long duration) {
        target.animate().translationY(end - start).setDuration(duration)
                .start();
    }


    public static void translationYBy(View target, int start, int end, long duration) {
        target.animate().translationYBy(end - start).setDuration(duration)
                .start();
    }
}
