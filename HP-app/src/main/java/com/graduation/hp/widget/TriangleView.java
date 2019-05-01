package com.graduation.hp.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.core.utils.AnimUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TriangleView extends AppCompatImageView {

    private boolean down = true;
    private TriangleViewClickListener listener;

    public interface TriangleViewClickListener {
        /**
         * 三角形控件点击事件
         *
         * @param v
         * @param down 三角形角尖是否朝下
         */
        void onClick(View v, boolean down);
    }

    public TriangleView(Context context) {
        this(context, null);
    }

    public TriangleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTriangleViewClickListener(TriangleViewClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("CheckResult")
    private void init() {
        setImageResource(R.mipmap.ic_triangle_down);
        RxView.clicks(this)
                .throttleFirst(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consume -> {
                    toggleAnimation();
                    if (listener != null) {
                        listener.onClick(this, down);
                    }
                    down = !down;
                });
    }

    private void toggleAnimation() {
        AnimUtils.rotate(this, down ? 0 : 180, down ? 180 : 0, 600);
    }


}
