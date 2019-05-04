package com.graduation.hp.core.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.graduation.hp.core.R;

/**
 * Abstract base class for a progress button.
 *
 * @author Chris Arriola
 */
public abstract class ProgressButton extends RelativeLayout {

    /* Private Instance Members */

    private ProgressBar mProgressBar;
    private ColorStateList mTint;

    public ProgressButton(Context context) {
        this(context, null);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.progressButtonStyle);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    /* View Methods */

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getView().setEnabled(enabled);
    }

    /* Public Methods */

    /**
     * @return The default LayoutParams for this View.
     */
    public static LayoutParams getDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * Method to indicate whether the progress indicator should be shown or not. If the
     * progress indicator is showing, this Button will by default will be disabled.
     *
     * @param shouldShow Set this to true if the progress indicator should be shown,
     *                   otherwise, false.
     */
    public void showProgressIndicator(boolean shouldShow) {

        if (shouldShow) {
            mProgressBar.setVisibility(View.VISIBLE);
            getView().setVisibility(View.INVISIBLE);
            setEnabled(false);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            getView().setVisibility(View.VISIBLE);
            setEnabled(true);
        }
    }

    /* Protected Methods */

    /**
     * @return The concrete View contained in this ProgressButton.
     */
    protected abstract View getView();

    /**
     * View initialization subclasses
     */
    protected abstract void onInitView(Context context, AttributeSet attrs, int defStyle);

    /* Private Methods */

    private void init(Context context, AttributeSet attrs, int defStyle) {

        // Subclass initialization
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ProgressButton,
                defStyle,
                0);

        final int progressIndicatorSize = a.getDimensionPixelSize(
                R.styleable.ProgressButton_progressIndicatorSize,
                getResources().getDimensionPixelSize(R.dimen.progress_button_indicator_size));
        final boolean showProgressBar = a.getBoolean(R.styleable.ProgressButton_showProgressIndicator,
                false);
        mTint = a.getColorStateList(R.styleable.ProgressButton_hpTint);

        a.recycle();

        // Progress Bar
        mProgressBar = new ProgressBar(context);
        final LayoutParams progressBarLayoutParams = new LayoutParams(progressIndicatorSize,
                progressIndicatorSize);
        progressBarLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mProgressBar.setLayoutParams(progressBarLayoutParams);

        // Initialize subclass specific View/s.
        onInitView(context, attrs, defStyle);
        addView(getView());

        showProgressIndicator(showProgressBar);

        addView(mProgressBar);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mTint != null && mTint.isStateful()) {
            updateTintColor();
        }
    }

    private void updateTintColor() {
        final int color = mTint.getColorForState(getDrawableState(), 0);
        final Drawable drawable = mProgressBar.getIndeterminateDrawable();
        if (drawable != null) {
            drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        }
    }
}
