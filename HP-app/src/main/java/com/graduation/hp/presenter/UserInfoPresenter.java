package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.navigation.user.info.UserInfoActivity;

import javax.inject.Inject;

public class UserInfoPresenter extends BasePresenter<UserInfoActivity, UserModel> {


    @Inject
    public UserInfoPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }
}
