package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.SearchContact;
import com.graduation.hp.repository.model.impl.SearchModel;
import com.graduation.hp.ui.search.SearchActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

public class SearchPresenter extends BasePresenter<SearchActivity, SearchModel>
        implements SearchContact.Presenter {


    @Inject
    public SearchPresenter(SearchModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getSearchRecords() {
        mMvpModel.addSubscribe(mMvpModel.getSearchKeywords()
                .subscribe(
                        searchKeywords -> EventBus.getDefault().post(searchKeywords),
                        throwable -> handlerApiError(throwable)));
    }

    @Override
    public void saveSearchRecord(String keyword) {
        mMvpModel.addSubscribe(mMvpModel.saveSearchKeyword(keyword)
                .subscribe(
                        result -> mMvpView.startSearchResultPage(keyword),
                        throwable -> handlerApiError(throwable)));
    }

}
