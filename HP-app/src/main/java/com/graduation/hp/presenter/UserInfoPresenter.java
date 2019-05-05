package com.graduation.hp.presenter;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.UserInfoContact;
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.repository.model.impl.UploadModel;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.navigation.user.info.UserInfoActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import javax.inject.Inject;

public class UserInfoPresenter extends BasePresenter<UserInfoActivity, UserModel>
        implements UserInfoContact.Presenter {

    @Inject
    UploadModel uploadModel;


    @Inject
    public UserInfoPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getCurrentUserInfo() {
        mMvpModel.addSubscribe(mMvpModel.getCurrentInfo()
                .subscribe(
                        user -> mMvpView.onGetUserInfoSuccess(user),
                        throwable -> handlerApiError(throwable)));
    }

    @Override
    public void logout() {
        mMvpModel.addSubscribe(mMvpModel.logout()
                .subscribe());
    }

    @Override
    public void updateUserInfo(int type, User user) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showMessage(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.updateUserInfo()
                .subscribe());
    }

    @Override
    public void uploadUserProfile(File file) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showMessage(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpView.showDialog(HPApplication.getStringById(R.string.tips_upload_ing));
        mMvpModel.addSubscribe(uploadModel.uploadFile(file)
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(event -> EventBus.getDefault().post(event),
                        throwable -> handlerApiError(throwable)));
    }
}
