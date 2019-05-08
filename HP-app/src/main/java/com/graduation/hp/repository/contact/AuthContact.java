package com.graduation.hp.repository.contact;

import com.graduation.hp.app.event.AuthEvent;
import com.graduation.hp.ui.auth.reset.UpdatePasswordFragment;

public interface AuthContact {

    interface View {
        void onRegisterInputError(AuthEvent authEvent);

        void onVerifyPhoneNumberResult(String phoneNumber);

        void onLoginSuccess();

        void onRegisterSuccess();

        void onSendCodeSuccess(String sourceCode);

    }

    interface Presenter extends UpdatePasswordFragment.UpdatePasswordFragmentListener {
        void onTryToLogin(String username, String password);

        void verifyCode(String phoneNumber, String code, String sourceCode);

        void onTryToSendCode(String phone);

        void onTryToSignup(String phone, String password, String repassword, String nickanme);
    }
}
