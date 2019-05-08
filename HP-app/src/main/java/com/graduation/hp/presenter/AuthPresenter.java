package com.graduation.hp.presenter;

import com.graduation.hp.R;
import com.graduation.hp.app.event.AuthEvent;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.utils.VerifyUtils;
import com.graduation.hp.repository.contact.AuthContact;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.auth.AuthActivity;

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
                }, throwable -> handlerApiError(throwable)));
    }

    @Override
    public void verifyCode(String phoneNumber, String code, String sourceCode) {
        mMvpModel.addSubscribe(Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            if (!VerifyUtils.isPhoneVerified(phoneNumber))
                emitter.onError(new ApiException(ResponseCode.INPUT_PHONE_NUMBER_ERROR));
            else if (!code.equals(sourceCode))
                emitter.onError(new ApiException(ResponseCode.INPUT_CODE_ERROR));
            emitter.onSuccess(true);
        }).subscribe(checkSuccess -> {
            mMvpView.showMessage(HPApplication.getStringById(R.string.tips_verify_phone_success));
            mMvpView.onVerifyPhoneNumberResult(phoneNumber);
        }, throwable -> {
            if (throwable instanceof ApiException) {
                ApiException exception = (ApiException) throwable;
                mMvpView.onRegisterInputError(new AuthEvent(AuthActivity.FRAGMENT_IS_INPUT_PHONE,
                        exception.getCode(), exception.getMessage()));
            } else {
                handlerApiError(throwable);
            }
        }));
    }

    @Override
    public void onTryToSendCode(String phone) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.sendVerificationMessage(phone)
                .subscribe(
                        code -> {
                            mMvpView.showMessage(HPApplication.getStringById(R.string.tips_send_success));
                            mMvpView.onSendCodeSuccess(String.valueOf(code));
                        },
                        throwable -> {
                            if (throwable instanceof ApiException) {
                                ApiException exception = (ApiException) throwable;
                                mMvpView.onRegisterInputError(new AuthEvent(AuthActivity.FRAGMENT_IS_INPUT_PHONE,
                                        exception.getCode(), exception.getMessage()));
                            } else {
                                handlerApiError(throwable);
                            }
                        })
        );
    }

    @Override
    public void onTryToSignup(String phone, String password, String repassword, String nickanme) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpView.showDialog(HPApplication.getStringById(R.string.tips_signup_ing));
        mMvpModel.addSubscribe(mMvpModel.signup(phone, password, repassword, nickanme)
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(success -> {
                    mMvpView.showMessage(HPApplication.getStringById(R.string.tips_signup_success));
                    mMvpView.onRegisterSuccess();
                }, throwable -> {
                    if (throwable instanceof ApiException) {
                        ApiException exception = (ApiException) throwable;
                        mMvpView.onRegisterInputError(new AuthEvent(AuthActivity.FRAGMENT_IS_SIGN_UP, exception.getCode(),
                                exception.getMessage()));
                    } else {
                        handlerApiError(throwable);
                    }
                }));
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
                .subscribe(result -> mMvpView.onRegisterSuccess(),
                        throwable -> {
                            if (throwable instanceof ApiException) {
                                ApiException exception = (ApiException) throwable;
                                mMvpView.onRegisterInputError(new AuthEvent(AuthActivity.FRAGMENT_IS_UPDATE_PASSWORD, exception.getCode(),
                                        exception.getMessage()));
                            } else {
                                handlerApiError(throwable);
                            }
                        }));
    }
}
