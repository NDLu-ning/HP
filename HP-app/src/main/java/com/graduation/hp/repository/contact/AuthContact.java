package com.graduation.hp.repository.contact;

import com.graduation.hp.ui.auth.login.LoginFragment;
import com.graduation.hp.ui.auth.register.RegisterFragment;
import com.graduation.hp.ui.auth.reset.InputPhoneFragment;

public interface AuthContact {

    interface View {
    }

    interface Presenter extends LoginFragment.LoginFragmentListener,
            RegisterFragment.RegisterFragmentListener,
            InputPhoneFragment.InputPhoneFragmentListener {

    }
}
