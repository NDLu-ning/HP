package com.graduation.hp.ui.auth.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Ning on 2019/4/24.
 */
public class LoginFragment extends BaseFragment {

    private LoginFragmentListener mCallback;

    public interface LoginFragmentListener {
        void onTryToLogin(String username, String password);

        void goToResetPassword();

        void goToRegister();
    }

    @BindView(R.id.login_password_et)
    AppCompatEditText loginPasswordEt;

    @BindView(R.id.login_username_et)
    AppCompatEditText loginUsernameEt;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        try {
            mCallback = (LoginFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement LoginFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        view.findViewById(R.id.register_tv).setOnClickListener(v -> mCallback.goToRegister());
        view.findViewById(R.id.forget_tv).setOnClickListener(v -> mCallback.goToResetPassword());
        view.findViewById(R.id.login_clear_iv).setOnClickListener(v -> loginUsernameEt.setText(""));
        view.findViewById(R.id.login_btn).setOnClickListener(v -> mCallback.onTryToLogin(loginUsernameEt.getText().toString(), loginPasswordEt.getText().toString()));
    }

    @OnCheckedChanged(R.id.login_password_cb)
    public void onPasswordShowOrHidden(boolean visible) {
        int pos = loginPasswordEt.getSelectionStart();
        loginPasswordEt.setTransformationMethod(visible ? HideReturnsTransformationMethod.getInstance()
                : PasswordTransformationMethod.getInstance());            //如果选中，显示密码
        loginPasswordEt.setSelection(pos);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }
}
