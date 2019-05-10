package com.graduation.hp.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.hp.R;

/**
 * 底部导航栏
 */
public class NavigationTabView extends FrameLayout {

    private static final int ICON_SIZE_ACTIVE =  R.dimen.tab_icon_size_selected;
    private static final int ICON_SIZE_INACTIVE = R.dimen.tab_icon_size_inactive;

    private static final int COLOR_ACTIVE = R.color.md_green_300;
    private static final int COLOR_INACTIVE = R.color.md_grey_400;

    private ImageView mIconImageView;
    private TextView mBadgeTextView;
    private TextView mNameTextView;

    private int mBadgeCount;

    public NavigationTabView(Context context) {
        this(context, null);
    }

    public NavigationTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.navigation_tab_item, this, true);
        mIconImageView = (ImageView) view.findViewById(R.id.tab_image_iv);
        mBadgeTextView = (TextView) view.findViewById(R.id.tab_badge_tv);
        mNameTextView = (TextView) view.findViewById(R.id.tab_name_tv);
    }

    public void onTabSelected() {
        setIconSize(ICON_SIZE_ACTIVE);
        setNameTextColor(COLOR_ACTIVE);
    }

    public void onTabUnselected() {
        setIconSize(ICON_SIZE_INACTIVE);
        setNameTextColor(COLOR_INACTIVE);
    }

    public void setIcon(int drawableResId) {
        mIconImageView.setImageResource(drawableResId);
    }

    public void setLabel(int stringResId) {
        mNameTextView.setText(stringResId);
    }

    public void setBadgeCount(int count) {
        mBadgeCount = count;
        if (mBadgeCount < 1) {
            mBadgeTextView.setVisibility(GONE);
        } else {
            mBadgeTextView.setText(getBadgeCountString());
            mBadgeTextView.setVisibility(VISIBLE);
        }
    }

    public void incrementBadgeCount(int increment) {
        mBadgeCount += increment;
        if (mBadgeCount < 1) {
            mBadgeTextView.setVisibility(GONE);
        } else {
            mBadgeTextView.setText(getBadgeCountString());
            mBadgeTextView.setVisibility(VISIBLE);
        }
    }

    private void setIconSize(int size) {
        final int iconSizePx;
        if (size == ICON_SIZE_INACTIVE) {
            iconSizePx = getResources().getDimensionPixelSize(ICON_SIZE_INACTIVE);
        } else if (size == ICON_SIZE_ACTIVE) {
            iconSizePx = getResources().getDimensionPixelSize(ICON_SIZE_ACTIVE);
        } else {
            throw new IllegalArgumentException("Invalid icon size.");
        }
        final ViewGroup.LayoutParams params = mIconImageView.getLayoutParams();
        params.width = iconSizePx;
        params.height = iconSizePx;
        mIconImageView.requestLayout();
    }

    private void setNameTextColor(int colorResId) {
        mNameTextView.setTextColor(ContextCompat.getColor(getContext(), colorResId));
    }

    private String getBadgeCountString() {
        return mBadgeCount > 99 ? "99+" : String.valueOf(mBadgeCount);
    }
}
