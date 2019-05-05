package com.graduation.hp.core.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;

public class ActivityUtils {

    private static final String TAG = ActivityUtils.class.getSimpleName();

    public static boolean startActivity(Context context, Intent intent) {
        if (context == null || intent == null) {
            Log.e(TAG, "startActivityForResult() : context and intent must not be null.");
            return false;
        }

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            onActivityNotFoundException(e);
            return false;
        }
        return true;
    }

    public static void startActivity(Fragment fragment, Intent intent) {
        if (fragment == null || intent == null) {
            Log.e(TAG, "startActivity() : fragment and intent must not be null.");
            return;
        }

        try {
            fragment.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            onActivityNotFoundException(e);
        }
    }

    public static boolean startActivityForResult(Activity activity, Intent intent, int requestCode) {
        if (activity == null || intent == null) {
            Log.e(TAG, "startActivity() : activity and intent must not be null.");
            return false;
        }
        try {
            activity.startActivityForResult(intent, requestCode);
            return true;
        } catch (ActivityNotFoundException e) {
            onActivityNotFoundException(e);
            return false;
        }
    }

    public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode) {
        if (fragment == null || intent == null) {
            Log.e(TAG, "startActivityForResult() : fragment, intent, and requestCode must not be null.");
            return;
        }
        try {
            fragment.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            onActivityNotFoundException(e);
        }
    }

    /**
     * create an intent for picking up arbitrary file.
     */
    public static Intent createFilePickerIntent() {
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        return intent;
    }

    public static void launchInBrowser(Context context, Uri unidentifiableUri) {
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW, unidentifiableUri);
        final PackageManager packageManager = context.getPackageManager();
        if (browserIntent.resolveActivity(packageManager) == null
                || !browserIntent.resolveActivityInfo(packageManager, browserIntent.getFlags()).exported) {
//            ToastUtil.showLong(R.string.error_no_browser);
        } else {
            startActivity(context, browserIntent);
        }
    }

    private static void onActivityNotFoundException(ActivityNotFoundException e) {
//        ToastUtil.showShort(R.string.activity_not_found);
    }
}
