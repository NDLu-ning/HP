package com.graduation.hp.presenter;

import com.graduation.hp.R;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.repository.contact.UserArticleContact;
import com.graduation.hp.repository.model.impl.ArticleModel;
import com.graduation.hp.ui.navigation.user.center.UserArticleFragment;

import javax.inject.Inject;

public class UserArticlePresenter extends BasePresenter<UserArticleFragment, ArticleModel>
        implements UserArticleContact.Presenter {

    private Page page;


    @Inject
    public UserArticlePresenter(ArticleModel mMvpModel) {
        super(mMvpModel);
        page = new Page();
    }

    @Override
    public void downloadInitialNewsList(long userId) {
        mMvpView.showLoading();
        loadMoreNewsList(State.STATE_INIT, userId);
    }

    @Override
    public void loadMoreNewsList(State state, long userId) {
        setCurState(state);
        if (isRefresh()) {
            page = new Page();
        }
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.getArticleListByUserId(userId, page.getOffset(), page.getLimit())
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(newsList -> {
                    mMvpView.onGetNewsListSuccess(newsList);
                    mMvpView.showMain();
                }, throwable -> {
                    handlerApiError(throwable);
                    mMvpView.showError(HPApplication.getStringById(R.string.tips_error_general));
                }));
    }
}
