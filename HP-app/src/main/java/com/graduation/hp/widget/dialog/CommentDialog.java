package com.graduation.hp.widget.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.graduation.hp.R;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.core.utils.ScreenUtils;
import com.graduation.hp.core.utils.ToastUtils;

public class CommentDialog extends DialogFragment {

    private Dialog mDialog;
    private ProgressDialog mProgressDialog;
    private CommentDialogClickListener mListener;
    private EditText mEditText;
    private int mMaximumCount = 150;
    private String hint;

    public CommentDialog() {
    }

    @SuppressLint("ValidFragment")
    public CommentDialog(String hint, CommentDialogClickListener listener) {
        this.mListener = listener;
        this.hint = hint;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View contentView = View.inflate(getActivity(), R.layout.dialog_comment_layout, null);
        mDialog.setContentView(contentView);
        mDialog.setCanceledOnTouchOutside(true);
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.height = ScreenUtils.dip2px(getContext(), 180.0F);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        final Button mSendBtn = contentView.findViewById(R.id.dialog_publish_btn);
        mEditText = contentView.findViewById(R.id.dialog_input_et);
        mEditText.setHint(hint);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0){
                    mSendBtn.setEnabled(true);
                }else {
                    mSendBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mSendBtn.setOnClickListener(v -> {
            String content = mEditText.getText().toString();
            if (TextUtils.isEmpty(content)) {
                ToastUtils.show(getContext(), getString(R.string.tips_content_not_null));
                return;
            } else {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();
                mProgressDialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
                    if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        if (mListener != null && isAdded()) {
                            mListener.onDialogBackPressed();
                        }
                        hideProgressDialog();
                        return true;
                    }
                    return false;
                });
                if (mListener != null && isAdded()) {
                    mListener.onSendMessage(mEditText.getText().toString());
                }
            }
        });
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        mDialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                if (mListener != null && isAdded()) {
                    mListener.onDialogBackPressed();
                }
                hideProgressDialog();
                return true;
            }
            return false;
        });
        return mDialog;
    }

    public boolean isShowing() {
        return isAdded() && (mDialog != null && mDialog.isShowing()
                || mProgressDialog != null && mProgressDialog.isShowing());
    }

    private void hideProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideSoftKeyboard() {
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {

        }
    }

    public interface CommentDialogClickListener {

        void onDialogBackPressed();

        void onSendMessage(String content);
    }

    @Override
    public void dismiss() {
        if (isAdded()) {
            hideProgressDialog();
            hideSoftKeyboard();
        }
        super.dismiss();
    }
}
