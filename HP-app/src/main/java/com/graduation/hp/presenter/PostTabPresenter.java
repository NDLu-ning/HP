package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.PostTabContact;
import com.graduation.hp.repository.model.impl.PostModel;
import com.graduation.hp.ui.navigation.post.PostTabFragment;

import javax.inject.Inject;

public class PostTabPresenter extends BasePresenter<PostTabFragment, PostModel>
        implements PostTabContact {

    @Inject
    public PostTabPresenter(PostModel mMvpModel) {
        super(mMvpModel);
    }
}
