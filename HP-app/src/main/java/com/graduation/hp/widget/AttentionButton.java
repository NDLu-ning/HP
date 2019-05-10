package com.graduation.hp.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.core.utils.AnimUtils;


/**
 * 关注按钮
 */
public class AttentionButton extends AppCompatButton {

    private boolean focusOn = true;
    private AttentionButtonClickListener listener;

    public interface AttentionButtonClickListener {
        void onClick(View v, boolean down);
    }

    public AttentionButton(Context context) {
        this(context, null);
    }

    public AttentionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AttentionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setAttentionButtonClickListener(AttentionButtonClickListener listener) {
        this.listener = listener;
    }

    private void init() {
        setBackgroundResource(R.drawable.selector_focus_btn);
        setTextColor(getResources().getColor(R.color.selector_focus_tv));
        setText(getResources().getString(R.string.action_focused));
        setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(this, focusOn);
            }
        });
    }

    public void setFocusOn(boolean focusOn) {
        this.focusOn = focusOn;
        if (!focusOn) {
            setBackgroundResource(R.drawable.selector_focus_btn);
            setTextColor(getResources().getColor(R.color.selector_focus_tv));
            setText(getResources().getString(R.string.action_focused));
        } else {
            setBackgroundResource(R.drawable.selector_unfocus_btn);
            setTextColor(getResources().getColor(R.color.selector_unfocus_tv));
            setText(getResources().getString(R.string.action_not_focused));
        }
    }

    public boolean isFocusOn() {
        return focusOn;
    }
}
