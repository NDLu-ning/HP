package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.NewsCommentContact;
import com.graduation.hp.repository.model.impl.CommentModel;
import com.graduation.hp.ui.navigation.news.comment.NewsCommentFragment;

import javax.inject.Inject;

public class NewsCommentPresenter extends BasePresenter<NewsCommentFragment, CommentModel>
        implements NewsCommentContact.Presenter {

    @Inject
    public NewsCommentPresenter(CommentModel mMvpModel) {
        super(mMvpModel);
    }
}
