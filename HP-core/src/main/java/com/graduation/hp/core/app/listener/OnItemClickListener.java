package com.graduation.hp.core.app.listener;

import android.view.View;

/**
 * 页面点击事件监听
 */
public interface OnItemClickListener {
    void OnItemClick(View view, int position);

    void OnItemClick(View view, Object object);

    void OnItemClick(View view, Object object, int position);

    void OnItemCheckChange(View view, Object object, boolean isChecked);
}
