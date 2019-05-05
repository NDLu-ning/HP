package com.graduation.hp.presenter;


import android.annotation.SuppressLint;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.contact.SettingContact;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;
import com.graduation.hp.ui.setting.SettingActivity;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

/**
 * Created by Ning on 2019/2/17.
 */

public class SettingPresenter extends BasePresenter<SettingActivity, UserModel>
        implements SettingContact.Presenter {

    @Inject
    public SettingPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }


    @Override
    public void onVersionControlClick() {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showMessage(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpView.showDialog(HPApplication.getStringById(R.string.tips_connect_server));
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mMvpView.showMessage("暂无最新版本信息");
                    mMvpView.dismissDialog();
                });
    }

    @Override
    public void getCurrentUserInfo() {
        mMvpModel.addSubscribe(mMvpModel.getCurrentInfo()
                .subscribe(user -> mMvpView.onGetUserInfoSuccess(user),
                        throwable -> handlerApiError(throwable)));
    }

    @Override
    public int getTextSize() {
        RepositoryHelper repositoryHelper = mMvpModel.getRepositoryHelper();
        PreferencesHelper preferencesHelper = repositoryHelper.getPreferencesHelper();
        return preferencesHelper.getTextSize();
    }

    @Override
    public void updateTextSize(int textSize) {
        RepositoryHelper repositoryHelper = mMvpModel.getRepositoryHelper();
        PreferencesHelper preferencesHelper = repositoryHelper.getPreferencesHelper();
        preferencesHelper.updateTextSize(textSize);
        mMvpView.operateLocalTextSizeSuccess(textSize);
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
//        mMvpModel.addSubscribe(mMvpModel.logout()
//                .subscribe(success -> {
//                    mMvpModel.clearUserInfo();
//                    mMvpView.onLogoutSuccess();
//                }, throwable -> handlerApiError(throwable)));
    }
}
