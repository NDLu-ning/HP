package com.graduation.hp.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.core.utils.AnimUtils;

public class LikeButton extends AppCompatImageView {

    private boolean liked = true;
    private LikeButtonClickListener listener;

    public interface LikeButtonClickListener {
        void onClick(View v, boolean down);
    }

    public LikeButton(Context context) {
        this(context, null);
    }

    public LikeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setLikeButtonClickListener(LikeButtonClickListener listener) {
        this.listener = listener;
    }

    private void init() {
        setImageResource(R.drawable.selector_unliked_btn);
        setOnClickListener(v -> {
            AnimUtils.setScaleAnimation(this, 600);
            setLiked(!liked);
            if (listener != null) {
                listener.onClick(this, liked);
            }
            liked = !liked;
        });
    }

    public void setLiked(boolean liked){
        if (liked) {
            setImageResource(R.drawable.selector_liked_btn);
        } else {
            setImageResource(R.drawable.selector_unliked_btn);
        }
    }

    public boolean isLiked(){
        return liked;
    }
}
