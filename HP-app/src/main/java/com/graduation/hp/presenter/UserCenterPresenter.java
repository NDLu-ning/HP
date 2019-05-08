package com.graduation.hp.presenter;

import com.graduation.hp.R;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.repository.contact.UserCenterContact;
import com.graduation.hp.repository.model.impl.AttentionModel;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.navigation.user.center.UserCenterActivity;

import javax.inject.Inject;

public class UserCenterPresenter extends BasePresenter<UserCenterActivity, UserModel>
        implements UserCenterContact.Presenter {

    @Inject
    AttentionModel attentionModel;

    @Inject
    public UserCenterPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void onGetUserInfo(long id) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.getUserInfo(id)
                .subscribe(
                        result -> mMvpView.onGetUserSuccess(result),
                        throwable -> handlerApiError(throwable)
                )
        );
    }

    @Override
    public void attentionUser(long authorId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(attentionModel.focusUser(authorId)
                .subscribe(
                        isFocusOn -> mMvpView.operateAttentionStateSuccess(isFocusOn),
                        throwable -> {
                            if (throwable instanceof ApiException) {
                                ApiException apiException = (ApiException) throwable;
                                if (apiException.getCode() == ResponseCode.TOKEN_ERROR.getStatus()) {
                                    mMvpView.showMessage(HPApplication.getStringById(R.string.tips_please_login_first));
                                    return;
                                }
                            }
                            handlerApiError(throwable);
                        }));
    }

    @Override
    public void isFocusOn(long authorId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(attentionModel.isFocusOn(authorId)
                .subscribe(
                        isFocusOn -> mMvpView.onGetAttentionSuccess(isFocusOn),
                        throwable -> {
                            if (throwable instanceof ApiException) {
                                ApiException apiException = (ApiException) throwable;
                                if (apiException.getCode() == ResponseCode.TOKEN_ERROR.getStatus()) {
                                    mMvpView.onGetAttentionSuccess(false);
                                    return;
                                }
                            }
                            handlerApiError(throwable);
                        }));
    }
}
