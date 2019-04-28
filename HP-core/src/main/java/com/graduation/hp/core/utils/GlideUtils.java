package com.graduation.hp.core.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.graduation.hp.core.R;

/**
 * Created by Win on 2018/5/3.
 */

public class GlideUtils {

    public static void loadUserHead(final ImageView imageView, String url) {
        String imgUrl;
        if (TextUtils.isEmpty(url)) {
            imgUrl = "";
        } else {
            if (url.startsWith("http")) {
                imgUrl = url;
            } else {
                imgUrl = "http://" + url;
            }
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_user_default_photo)
                .error(R.mipmap.ic_user_default_photo)
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
        Glide.with(imageView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    public static void loadUserHead(final ImageView imageView, String url, int width, int height) {
        String imgUrl;
        if (TextUtils.isEmpty(url)) {
            imgUrl = "";
        } else {
            if (url.startsWith("http")) {
                imgUrl = url;
            } else {
                imgUrl = "http://" + url;
            }
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_user_default_photo)
                .error(R.mipmap.ic_user_default_photo)
                .override(width, height)
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadImage(final ImageView imageView, String url) {
        String imgUrl;
        if (TextUtils.isEmpty(url)) {
            imgUrl = "";
        } else {
            if (url.startsWith("http")) {
                imgUrl = url;
            } else {
                imgUrl = "http://" + url;
            }
        }
        RoundedCorners roundedCorners = new RoundedCorners(3);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_default_pic)
                .error(R.mipmap.ic_default_pic)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

        Glide.with(imageView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    public static void loadImage(final ImageView imageView, String url, int width, int height) {
        String imgUrl;
        if (TextUtils.isEmpty(url)) {
            imgUrl = "";
        } else {
            if (url.startsWith("http")) {
                imgUrl = url;
            } else {
                imgUrl = "http://" + url;
            }
        }

        RoundedCorners roundedCorners = new RoundedCorners(3);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners)
                .placeholder(R.mipmap.ic_default_pic)
                .error(R.mipmap.ic_default_pic)
                .fitCenter()
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

        Glide.with(imageView.getContext())
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }
}
