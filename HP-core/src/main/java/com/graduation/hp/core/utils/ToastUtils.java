package com.graduation.hp.core.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Win on 2018/4/21.
 */

public class ToastUtils {
    private static Toast mToast = null;

    public static void show(Context context, String text) {
        showText(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String text, int duration) {
        showText(context, text, duration);
    }

    public static void show(Context context, int textId) {
        showText(context, context.getText(textId).toString(), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int textId, int duration) {
        showText(context, context.getText(textId).toString(), duration);
    }

    private static void showText(Context context, String text, int dutation) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, dutation);
        }
        mToast.setText(text);
        mToast.show();
    }
//
//    public static void showCustom(Context context, int duration, OnCustomInflateListener inflateListener, OnItemClickListener listener) {
//        Toast toast = new Toast(context);
//        try {
//            Object mTN;
//            mTN = CommonUtils.getField(toast, "mTN");
//            if (mTN != null) {
//                Object mParams = CommonUtils.getField(mTN, "mParams");
//                if (mParams != null
//                        && mParams instanceof WindowManager.LayoutParams) {
//                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
//                    //Toast可点击
//                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        View view = inflateListener.onInflaterView(context, listener);
//        if (view == null) {
//            throw new IllegalArgumentException("Toast 's View could not be null");
//        }
//        toast.setView(view);
//        toast.setDuration(duration);
//        toast.show();
//    }
//
//
//    public static void showCollectedSuccessToast(Context context, OnItemClickListener listener) {
//        showCustom(context, Toast.LENGTH_LONG, new OnCustomInflateListener() {
//            @Override
//            public View onInflaterView(Context context, final OnItemClickListener clickListener) {
//                View view = LayoutInflater.from(context).inflate(R.layout.view_collected_toast, null);
//                AppCompatTextView textView = view.findViewById(R.id.view_collected_toast_tv);
//                textView.setText(Html.fromHtml(context.getResources().getString(R.string.tips_collected_success)));
//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (clickListener != null) {
//                            clickListener.OnItemClick(view, -2);
//                        }
//                    }
//                });
//                return view;
//            }
//        }, listener);
//    }
}
