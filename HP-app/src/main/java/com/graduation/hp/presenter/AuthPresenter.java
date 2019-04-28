package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.repository.contact.AuthContact;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.auth.AuthActivity;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class AuthPresenter extends BasePresenter<AuthActivity, UserModel>
        implements AuthContact.Presenter {

    @Inject
    public AuthPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void onTryToLogin(String username, String password) {
        mMvpModel.addSubscribe(mMvpModel.login(username, password)
                .subscribe(result -> {

                }, throwable -> {

                }));
    }

    @Override
    public void goToResetPassword() {

    }

    @Override
    public void goToRegister() {

    }

    @Override
    public void onTryToSignup() {
//        mMvpModel.addSubscribe();
    }

    @Override
    public void verifyPhoneNumber(String phoneNumber) {

    }
}
