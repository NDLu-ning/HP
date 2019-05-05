package com.graduation.hp.presenter;

import com.graduation.hp.R;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.repository.contact.UserNewsContact;
import com.graduation.hp.repository.model.impl.NewsModel;
import com.graduation.hp.ui.navigation.user.center.UserNewsFragment;

import javax.inject.Inject;

public class UserNewsPresenter extends BasePresenter<UserNewsFragment, NewsModel>
        implements UserNewsContact.Presenter {

    private Page page;


    @Inject
    public UserNewsPresenter(NewsModel mMvpModel) {
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
        mMvpModel.addSubscribe(mMvpModel.getNewsListByUserId(userId, page.getOffset(), page.getLimit())
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
