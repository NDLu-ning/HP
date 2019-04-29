package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.repository.contact.AuthContact;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.auth.AuthActivity;
import com.graduation.hp.utils.VerifyUtils;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
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
                .doOnSuccess(result -> {

                })
                .subscribe(result -> {

                }, this::handlerApiError));
    }

    @Override
    public void goToResetPassword() {

    }

    @Override
    public void goToRegister() {

    }

    @Override
    public void verifyPhoneNumber(String phoneNumber) {
        mMvpModel.addSubscribe(Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            emitter.onSuccess(VerifyUtils.isPhoneVerified(phoneNumber));
        }).subscribe(checkSuccess -> mMvpView.onVerifyPhoneNumberResult(checkSuccess?phoneNumber:"")));
    }

    @Override
    public void onTryToSignup(String username, String password, String repassword, String phoneNumber) {
        mMvpModel.addSubscribe(mMvpModel.signup(username, password, repassword, phoneNumber)
                .subscribe());
    }
}
