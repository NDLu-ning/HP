package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.UserInfoContact;
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.navigation.user.info.UserInfoActivity;

import javax.inject.Inject;

public class UserInfoPresenter extends BasePresenter<UserInfoActivity, UserModel>
        implements UserInfoContact.Presenter {


    @Inject
    public UserInfoPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getCurrentUserInfo() {
        mMvpModel.addSubscribe(mMvpModel.getCurrentInfo()
                .subscribe(
                        user -> mMvpView.onGetUserInfoSuccess(user),
                        throwable -> handlerApiError(throwable))
        );
    }

    @Override
    public void logout() {

    }

    @Override
    public void skipToUserUpdateView(int type, User user) {

    }
}
