package com.graduation.hp.core.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.graduation.hp.core.R;

/**
 * Custom button with text that has a loading indicator.
 */
public class ProgressTextButton extends ProgressButton {

    private TextView mTextView;

    public ProgressTextButton(Context context) {
        this(context, null);
    }

    public ProgressTextButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressTextButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setText(int textResId) {
        mTextView.setText(textResId);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public String getText() {
        return mTextView.getText().toString();
    }

    public void setTextColor(ColorStateList colorStateList) {
        mTextView.setTextColor(colorStateList);
    }

    @Override
    protected void onInitView(Context context, AttributeSet attrs, int defStyle) {

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ProgressButton,
                defStyle,
                R.style.Button_Green);

        final String text = a.getString(R.styleable.ProgressButton_android_text);
        final ColorStateList textColor = a.getColorStateList(R.styleable.ProgressButton_android_textColor);
        final int textSize = a.getDimensionPixelSize(R.styleable.ProgressButton_android_textSize, 15);

        a.recycle();

        mTextView = new TextView(context);

        mTextView.setText(text);
        mTextView.setTextColor(textColor);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        final LayoutParams textViewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        textViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mTextView.setLayoutParams(textViewLayoutParams);

    }

    @Override
    protected View getView() {
        return mTextView;
    }
}
