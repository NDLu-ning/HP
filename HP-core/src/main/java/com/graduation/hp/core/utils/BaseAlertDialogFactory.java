package com.graduation.hp.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.graduation.hp.core.R;


public class BaseAlertDialogFactory {

    protected static final DialogInterface.OnClickListener NEGATIVE_BUTTON_ON_CLICK_LISTENER =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            };

    public static void showRequestPermissionDialog(Activity activity, int messageResId,
                                                   DialogInterface.OnClickListener onClickListener) {
        if (canShowDialog(activity)) {
            getBuilder(activity)
                    .setTitle(R.string.requesting_permission)
                    .setMessage(messageResId)
                    .setPositiveButton(android.R.string.yes, onClickListener)
                    .setNegativeButton(android.R.string.no, NEGATIVE_BUTTON_ON_CLICK_LISTENER)
                    .create().show();
        }
    }

    protected static AlertDialog.Builder getBuilder(Context context) {
        return new AlertDialog.Builder(context, R.style.MyAlertDialog);
    }

    protected static boolean canShowDialog(Activity activity) {
        return activity != null && !activity.isFinishing();
    }
}