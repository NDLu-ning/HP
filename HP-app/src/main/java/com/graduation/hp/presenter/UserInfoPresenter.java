package com.graduation.hp.presenter;

import com.graduation.hp.R;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.UserInfoContact;
import com.graduation.hp.repository.http.entity.vo.UserVO;
import com.graduation.hp.repository.model.impl.UploadModel;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.navigation.user.info.UserInfoActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpView.showDialog(HPApplication.getStringById(R.string.tips_logout_ing));
        mMvpModel.addSubscribe(Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(result -> {
                    mMvpModel.clearUserInfo();
                    mMvpView.onLogoutSuccess();
                }));
    }

    @Override
    public void updateUserInfo(int type, UserVO user) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showMessage(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.updateUserInfo(user)
                .subscribe(result -> {
                    mMvpView.showMessage(HPApplication.getStringById(R.string.tips_update_success));
                },throwable -> handlerApiError(throwable)));
    }

    @Override
    public void uploadUserProfile(File file) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showMessage(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpView.showDialog(HPApplication.getStringById(R.string.tips_upload_ing));
        mMvpModel.addSubscribe(uploadModel.uploadFile(file)
                .flatMap(event -> mMvpModel.updateUserProfile(event.getUrl()))
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(event -> EventBus.getDefault().post(event),
                        throwable -> handlerApiError(throwable)));
    }
}
