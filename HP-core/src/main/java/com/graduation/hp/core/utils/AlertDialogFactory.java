package com.graduation.hp.core.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.graduation.hp.core.R;

public class AlertDialogFactory extends BaseAlertDialogFactory {


    public static void showOkDialog(Activity activity, int titleResId, int messageResId) {
        showOkDialog(activity, titleResId, activity.getString(messageResId));
    }

    public static void showOkDialog(Activity activity, int titleResId, String messageResId) {
        if (canShowDialog(activity)) {
            final AlertDialog.Builder builder = getBuilder(activity);
            if (titleResId > 0) {
                builder.setTitle(titleResId);
            }
            builder.setMessage(messageResId);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();
        }
    }

    public static void showCameraNotFoundDialog(Activity activity) {
        showOkDialog(activity, R.string.no_camera_found, R.string.camera_not_supported);
    }
}
