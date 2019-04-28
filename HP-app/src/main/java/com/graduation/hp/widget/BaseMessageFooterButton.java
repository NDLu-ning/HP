package com.graduation.hp.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import com.graduation.hp.R;

public abstract class BaseMessageFooterButton extends android.support.v7.widget.AppCompatTextView {

    protected static final int COLOR_UNSELECTED = R.color.message_action_item_normal;
    protected static final int COLOR_SELECTED = R.color.message_action_item_selected;

    private static final int TEXT_SIZE = R.dimen.sp_14;
    private static final int DIMEN_DRAWABLE_PADDING = R.dimen.dp_4;

    public BaseMessageFooterButton(Context context) {
        this(context, null);
    }

    public BaseMessageFooterButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseMessageFooterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected abstract int getIconResId();

    protected void init() {
        final Context context = getContext();
        setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, getIconResId()),
                null, null, null);

        final Resources res = getResources();
        setCompoundDrawablePadding((int) res.getDimension(DIMEN_DRAWABLE_PADDING));
        setTextColor(ContextCompat.getColorStateList(context, COLOR_UNSELECTED));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, res.getDimension(TEXT_SIZE));
        setGravity(Gravity.CENTER_VERTICAL);
    }
}
