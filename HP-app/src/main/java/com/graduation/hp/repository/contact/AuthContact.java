package com.graduation.hp.repository.contact;

import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.ui.auth.login.LoginFragment;
import com.graduation.hp.ui.auth.register.RegisterFragment;
import com.graduation.hp.ui.auth.reset.InputPhoneFragment;

public interface AuthContact {

    interface View {
        void onRegisterInputError(ResponseCode responseCode);

        void onVerifyPhoneNumberResult(String phoneNumber);
    }

    interface Presenter extends LoginFragment.LoginFragmentListener,
            RegisterFragment.RegisterFragmentListener,
            InputPhoneFragment.InputPhoneFragmentListener {

    }
}
