package com.graduation.hp.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Ning on 2019/3/9.
 */

public class PermissionsUtils {

    private PermissionsUtils() {
    }


    public static void checkAndRequestPermissions(final FragmentActivity activity, int messageResId,
                                                  final String... permissions) {
        if (!hasPermission(activity, permissions)) {
            BaseAlertDialogFactory.showRequestPermissionDialog(activity, messageResId,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PermissionsUtils.requestPermissions(activity, permissions);
                        }
                    });
        }
    }

    public static boolean hasPermission(Activity activity, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void requestPermissions(Activity activity, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, 1);
    }

    public static void grantUriPermission(Context context, Uri uri) {
        context.getApplicationContext().grantUriPermission(context.getPackageName(), uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }
}
