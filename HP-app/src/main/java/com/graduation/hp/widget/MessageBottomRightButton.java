package com.graduation.hp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.graduation.hp.R;

public class MessageBottomRightButton extends FrameLayout {

    private static final String DEFAULT_MESSAGE_VALUE = "0";
    private AppCompatImageView mImageView;
    private AppCompatTextView mMessageView;

    public MessageBottomRightButton(Context context) {
        super(context);
    }

    public MessageBottomRightButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBottomRightButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.view_message_bottom_right_button, this);
        mImageView = findViewById(R.id.image_view);
        mMessageView = findViewById(R.id.bottom_right_num_tv);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MessageBottomRightButton,
                defStyle,
                0);
        String messageDefaultValue = a.getString(R.styleable.MessageBottomRightButton_messageDefaultValue);
        int messageTextColor = a.getColor(R.styleable.MessageBottomRightButton_messageTextColor, Color.WHITE);
        if (TextUtils.isEmpty(messageDefaultValue)) {
            messageDefaultValue = DEFAULT_MESSAGE_VALUE;
        }
        mMessageView.setTextColor(messageTextColor);
        mMessageView.setText(messageDefaultValue);


    }
}
