package com.graduation.hp.core.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 备注： 重写ViewPager 禁止ViewPager滑动
 */
public class BaseViewPager extends ViewPager {

    private boolean scrollable = false;

    public BaseViewPager(Context context) {
        super(context);
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable
     */
    public void setScrollable(boolean enable) {
        scrollable = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (scrollable) {
            return super.onInterceptTouchEvent(event);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollable) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}
