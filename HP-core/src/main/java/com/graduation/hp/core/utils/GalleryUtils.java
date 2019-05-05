package com.graduation.hp.core.utils;

import android.content.Intent;

public final class GalleryUtils {

    private static final String INTENT_TYPE_ALL_IMAGES = "image/*";
    private static final String INTENT_TYPE_ALL_VIDEOS = "video/*";

    public static Intent getPickImageFromGalleryIntent() {
        final Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(INTENT_TYPE_ALL_IMAGES);
        return intent;
    }

    public static Intent getPickVideoFromGalleryIntent() {
        final Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(INTENT_TYPE_ALL_VIDEOS);
        return intent;
    }
}