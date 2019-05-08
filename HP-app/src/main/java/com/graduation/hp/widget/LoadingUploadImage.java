package com.graduation.hp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.FrameLayout;

import com.graduation.hp.R;

public class LoadingUploadImage extends FrameLayout {

    private AppCompatImageView mImageView;
    private AppCompatImageView mCloseView;
    private FrameLayout mLoadingView;
    private boolean showClose = false;
    private boolean isLoading = true;
    private LoadingUploadImageListener mListener;
    private Bitmap bitmap;

    public LoadingUploadImage(@NonNull Context context, Bitmap bitmap, LoadingUploadImageListener listener) {
        super(context, null);
        this.bitmap = bitmap;
        this.mListener = listener;
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.adapter_post_add_picture_item, this);
        mImageView = findViewById(R.id.upload_image);
        mImageView.setImageBitmap(bitmap);
        mCloseView = findViewById(R.id.upload_close);
        mLoadingView = findViewById(R.id.view_loading);
        setOnLongClickListener(v -> {
            if (isLoading) return false;
            mCloseView.setVisibility(showClose ? GONE : VISIBLE);
            showClose = !showClose;
            return true;
        });

        mCloseView.setOnClickListener(v -> {
            if (mListener != null)
                mListener.onCancelUploading(this);
        });
    }

    public interface LoadingUploadImageListener {
        void onCancelUploading(View view);
    }

    public void setShowing(boolean show) {
        isLoading = show;
        mLoadingView.setVisibility(show ? VISIBLE : GONE);
    }


}
