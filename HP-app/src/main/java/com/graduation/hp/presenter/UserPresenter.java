package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BaseContact;
import com.graduation.hp.core.mvp.BasePresenter;

import javax.inject.Inject;

public class UserPresenter extends BasePresenter {


    @Inject
    public UserPresenter(BaseContact.Model mMvpModel) {
        super(mMvpModel);
    }
}
