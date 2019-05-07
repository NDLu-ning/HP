package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.PostCreationContact;
import com.graduation.hp.repository.model.impl.PostModel;
import com.graduation.hp.ui.navigation.post.create.PostCreationActivity;

import javax.inject.Inject;

public class PostCreationPresenter extends BasePresenter<PostCreationActivity, PostModel>
        implements PostCreationContact.Presenter {

    @Inject
    public PostCreationPresenter(PostModel mMvpModel) {
        super(mMvpModel);
    }


}
