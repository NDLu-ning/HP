package com.graduation.hp.repository.contact;

import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.ui.auth.login.LoginFragment;
import com.graduation.hp.ui.auth.register.RegisterFragment;
import com.graduation.hp.ui.auth.reset.InputPhoneFragment;
import com.graduation.hp.ui.auth.reset.UpdatePasswordFragment;

public interface AuthContact {

    interface View {
        void onRegisterInputError(ResponseCode responseCode);

        void onVerifyPhoneNumberResult(String phoneNumber);

        void onLoginSuccess();

        void onRegisterSuccess();

    }

    interface Presenter extends RegisterFragment.RegisterFragmentListener,
            InputPhoneFragment.InputPhoneFragmentListener,
            UpdatePasswordFragment.UpdatePasswordFragmentListener {
        void onTryToLogin(String username, String password);
    }
}
