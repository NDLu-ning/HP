package com.graduation.hp.ui.auth.register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.event.AuthEvent;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.ui.auth.AuthActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ning on 2019/4/24.
 */

public class RegisterFragment extends BaseFragment {

    public interface RegisterFragmentListener {

        void onTryToSignup(String phone, String password, String repassword, String nickname);
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @BindView(R.id.register_phone_til)
    TextInputLayout registerPhoneTil;

    @BindView(R.id.register_phone_et)
    AppCompatEditText registerPhoneEt;

    @BindView(R.id.register_password_til)
    TextInputLayout registerPasswordTil;

    @BindView(R.id.register_password_et)
    AppCompatEditText registerPasswordEt;

    @BindView(R.id.register_repassword_til)
    TextInputLayout registerRepasswordTil;

    @BindView(R.id.register_repassword_et)
    AppCompatEditText registerRepasswordEt;

    @BindView(R.id.register_nickname_til)
    TextInputLayout registerNicknameTil;

    @BindView(R.id.register_nickname_et)
    AppCompatEditText registerNicknameEt;


    private RegisterFragmentListener mListener;


    @Override
    public void onAttach(Context context) {
        try {
            mListener = (RegisterFragment.RegisterFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement InputPhoneFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
    }

    @OnClick(R.id.register_btn)
    public void onRegister() {
        String phone = registerPhoneEt.getText().toString();
        String password = registerPasswordEt.getText().toString();
        String repassword = registerRepasswordEt.getText().toString();
        String nickname = registerNicknameEt.getText().toString();
        mListener.onTryToSignup(phone, password, repassword, nickname);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterError(AuthEvent event) {
        if (!isAdded() || event.getFragment() != AuthActivity.FRAGMENT_IS_SIGN_UP) return;
        if (event.getCode() == ResponseCode.INPUT_PHONE_NUMBER_ERROR.getStatus()) {
            registerPhoneTil.setError(event.getMsg());
            registerPasswordTil.setError("");
            registerRepasswordTil.setError("");
            registerNicknameTil.setError("");
        } else if (event.getCode() == ResponseCode.INPUT_PASSWORD_ERROR.getStatus()) {
            registerPasswordTil.setError(event.getMsg());
            registerPhoneTil.setError("");
            registerRepasswordTil.setError("");
            registerNicknameTil.setError("");
        } else if (event.getCode() == ResponseCode.INPUT_REPEAT_PASSWORD_ERROR.getStatus()) {
            registerRepasswordTil.setError(event.getMsg());
            registerPhoneTil.setError("");
            registerPasswordTil.setError("");
            registerNicknameTil.setError("");
        } else if (event.getCode() == ResponseCode.INPUT_NICKNAME_ERROR.getStatus()) {
            registerNicknameTil.setError(event.getMsg());
            registerPhoneTil.setError("");
            registerPasswordTil.setError("");
            registerRepasswordTil.setError("");
        }
        showMessage(event.getMsg());
    }


    @Override
    public boolean useEventBus() {
        return true;
    }

}
