package com.graduation.hp.presenter;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.repository.contact.NewsListContact;
import com.graduation.hp.repository.model.impl.NewsModel;
import com.graduation.hp.ui.navigation.news.list.NewsListFragment;

import javax.inject.Inject;

public class NewsListPresenter extends BasePresenter<NewsListFragment, NewsModel>
        implements NewsListContact.Presenter {

    private Page page;

    @Inject
    public NewsListPresenter(NewsModel mMvpModel) {
        super(mMvpModel);
        this.page = new Page();
    }

    @Override
    public void downloadInitialData(long category) {
        mMvpView.showLoading();
        downloadMoreData(State.STATE_INIT, category);
    }

    @Override
    public void downloadMoreData(State state, long category) {
        setCurState(state);
        if (isRefresh()) {
            page = new Page();
        }
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.getNewsListByTypeId(category, page.getOffset(), page.getLimit())
                .doOnSuccess(newsList -> page.setOffset(page.getOffset() + page.getLimit()))
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(newsList -> {
                    mMvpView.onDownloadDataSuccess(newsList);
                    mMvpView.showMain();
                }, throwable -> {
                    handlerApiError(throwable);
                    mMvpView.showError(HPApplication.getStringById(R.string.tips_error_general));
                }));
    }
}
