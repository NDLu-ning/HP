package com.graduation.hp.presenter;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.AuthContact;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.auth.AuthActivity;
import com.graduation.hp.core.utils.VerifyUtils;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

public class AuthPresenter extends BasePresenter<AuthActivity, UserModel>
        implements AuthContact.Presenter {

    @Inject
    public AuthPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void onTryToLogin(String username, String password) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpView.showDialog(HPApplication.getStringById(R.string.tips_logging_in));
        mMvpModel.addSubscribe(mMvpModel.login(username, password)
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(result -> {
                    mMvpView.showMessage(HPApplication.getStringById(R.string.tips_login_success));
                    mMvpView.onLoginSuccess();
                }, throwable -> {
                    handlerApiError(throwable);
                }));
    }

    @Override
    public void verifyPhoneNumber(String phoneNumber) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            emitter.onSuccess(VerifyUtils.isPhoneVerified(phoneNumber));
        }).subscribe(checkSuccess -> mMvpView.onVerifyPhoneNumberResult(checkSuccess ? phoneNumber : "")));
    }

    @Override
    public void onTryToSignup(String username, String password, String repassword, String phoneNumber) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.signup(username, password, repassword, phoneNumber)
                .subscribe());
    }

    @Override
    public void updatePassword(String phoneNumber, String password, String repassword) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpView.showDialog(HPApplication.getStringById(R.string.tips_updating));
        mMvpModel.addSubscribe(mMvpModel.updatePassword(phoneNumber, password, repassword)
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(result -> {
                    mMvpView.onRegisterSuccess();
                }, throwable -> {
//                    mMvpView.onRegisterInputError();
                }));
    }
}
