package com.graduation.hp.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.graduation.hp.R;

public class LikeButton extends BaseMessageFooterButton {

    public LikeButton(Context context) {
        this(context, null);
    }

    public LikeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void toggleLikeStatus(int likeCount, boolean isLiked) {
        if (likeCount > 0) {
            setText(String.valueOf(likeCount));
        } else {
            setText("");
        }

        final Context context = getContext();
        if (isLiked) {
            setTextColor(ContextCompat.getColorStateList(context, COLOR_SELECTED));
            setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(getContext(), R.drawable.selector_liked_btn), null,
                    null, null);
        } else {
            setTextColor(ContextCompat.getColorStateList(context, COLOR_UNSELECTED));
            setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(getContext(), R.drawable.selector_unliked_btn), null,
                    null, null);
        }
    }

    @Override
    protected int getIconResId() {
        return R.drawable.selector_unliked_btn;
    }
}
