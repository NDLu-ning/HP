package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.UserPostContact;
import com.graduation.hp.repository.model.impl.PostModel;
import com.graduation.hp.ui.navigation.user.center.UserPostFragment;

import javax.inject.Inject;

public class UserPostPresenter extends BasePresenter<UserPostFragment, PostModel>
        implements UserPostContact.Presenter {

    @Inject
    public UserPostPresenter(PostModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void downloadInitialPostList() {

    }


}
