package com.graduation.hp.core.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.graduation.hp.core.R;
import com.graduation.hp.core.app.listener.OnItemClickListener;


/**
 * Created by Ning on 2019/2/16.
 */

public class DialogUtils {

    private DialogUtils() {
    }

    public static ProgressDialog showLoadingDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.MyDialog);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        View view = LayoutInflater.from(context).inflate(R.layout.view_loading_dialog, null);
        TextView textView = view.findViewById(R.id.view_loading_dialog_tv);
        textView.setText(message);
        progressDialog.setContentView(view);
        return progressDialog;
    }

    public static AlertDialog showConfirmDialog(Context context, String title, String message, final OnItemClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.view_confirm_dialog, null);
        builder.setView(view);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
        TextView titleTv = view.findViewById(R.id.dialog_title_tv);
        titleTv.setText(title);
        TextView messageTv = view.findViewById(R.id.dialog_message_tv);
        messageTv.setText(message);
        view.findViewById(R.id.dialog_confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.dialog_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(v, 1);
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
