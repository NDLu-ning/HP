package com.graduation.hp.presenter;

import android.os.Bundle;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.repository.contact.ArticleListContact;
import com.graduation.hp.repository.model.impl.ArticleModel;
import com.graduation.hp.ui.navigation.article.list.ArticleListFragment;

import javax.inject.Inject;

public class ArticleListPresenter extends BasePresenter<ArticleListFragment, ArticleModel>
        implements ArticleListContact.Presenter {

    private Page page;

    @Inject
    public ArticleListPresenter(ArticleModel mMvpModel) {
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
        mMvpModel.addSubscribe(mMvpModel.getArticleListByTypeId(category, page.getOffset(), page.getLimit())
                .doOnSuccess(newsList -> page.setOffset(page.getOffset() + page.getLimit()))
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(newsList -> {
                    mMvpView.onDownloadDataSuccess(newsList);
                    mMvpView.showMain();
                }, throwable -> handlerApiError(throwable)));
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
