package com.graduation.hp.presenter;

import android.os.Bundle;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.repository.contact.ArticleCommentContact;
import com.graduation.hp.repository.model.impl.DiscussModel;
import com.graduation.hp.ui.navigation.article.comment.ArticleCommentFragment;

import javax.inject.Inject;

public class ArticleCommentPresenter extends BasePresenter<ArticleCommentFragment, DiscussModel>
        implements ArticleCommentContact.Presenter {

    private Page page;

    @Inject
    public ArticleCommentPresenter(DiscussModel mMvpModel) {
        super(mMvpModel);
        page = new Page();
    }

    @Override
    public void getArticleCommentList(State state, long articleId) {
        setCurState(state);
        if (isRefresh()) {
            page = new Page();
        }
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        if (state == State.STATE_INIT) {
            mMvpView.showLoading();
        }
        mMvpModel.addSubscribe(mMvpModel.getDiscuss(articleId)
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(articleDiscussPOS -> {
                    mMvpView.onGetArticleCommentsSuccess(state, articleDiscussPOS);
                    mMvpView.showMain();
                }, throwable -> handlerApiError(throwable)));
    }

    @Override
    public void addArticleComment(long articleId, String content, long talkerUserId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.discuss(articleId, content,
                talkerUserId == -1L ? DiscussModel.DISCUSS_COMMENT : DiscussModel.DISCUSS_REPLY,
                talkerUserId)
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(result -> mMvpView.operateArticleCommentStatus(true),
                        throwable -> {
                            mMvpView.operateArticleCommentStatus(false);
                            handlerApiError(throwable);
                        }));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Key.PAGE, page);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        page = savedInstanceState.getParcelable(Key.PAGE);
        if (page == null) {
            page = new Page();
        }
    }
}
