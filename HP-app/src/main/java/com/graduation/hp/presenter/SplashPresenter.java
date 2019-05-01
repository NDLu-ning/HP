package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.repository.contact.SplashContact;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.SplashActivity;

import javax.inject.Inject;

import io.reactivex.Single;

public class SplashPresenter extends BasePresenter<SplashActivity, UserModel>
        implements SplashContact.Presenter {

    @Inject
    public SplashPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void checkUserLoginStatus() {
        mMvpModel.addSubscribe(mMvpModel.isCurrentUserLogin()
                .flatMap(isCurUserLogin
                        -> Single.just(Result.build(isCurUserLogin ? ResponseCode.SUCCESS : ResponseCode.ERROR))
                ).subscribe(result
                        -> mMvpView.goToNextPage(result.getStatus().equals(ResponseCode.SUCCESS.getStatus()))));
    }
}
