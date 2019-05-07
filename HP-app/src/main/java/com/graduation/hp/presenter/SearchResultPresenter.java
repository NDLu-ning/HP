package com.graduation.hp.presenter;

import android.os.Bundle;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.repository.contact.SearchResultContact;
import com.graduation.hp.repository.model.impl.SearchModel;
import com.graduation.hp.ui.search.SearchResultFragment;

import javax.inject.Inject;

public class SearchResultPresenter extends BasePresenter<SearchResultFragment, SearchModel>
        implements SearchResultContact.Presenter {

    private Page page;

    @Inject
    public SearchResultPresenter(SearchModel mMvpModel) {
        super(mMvpModel);
        page = new Page();
    }


    @Override
    public void searchKeywordNewsList(State state, String keyword) {
        setCurState(state);
        if (state == State.STATE_INIT) {
            mMvpView.showLoading();
        }
        if (isRefresh()) {
            page = new Page();
        }
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.searchKeywords(keyword, page.getOffset(), page.getLimit())
                .doOnSuccess(newsList -> page.setOffset(page.getOffset() + page.getLimit()))
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(newsList -> {
                    mMvpView.onSearchKeywordSuccess(state, newsList);
                    mMvpView.showMain();
                }, throwable -> {
                    handlerApiError(throwable);
                    mMvpView.showError(HPApplication.getStringById(R.string.tips_error_general));
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
