package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.SearchContact;
import com.graduation.hp.repository.model.impl.NewsModel;
import com.graduation.hp.repository.model.impl.SearchModel;
import com.graduation.hp.ui.search.SearchActivity;

import javax.inject.Inject;

public class SearchPresenter extends BasePresenter<SearchActivity, SearchModel>
        implements SearchContact.Presenter {

    @Inject
    NewsModel newsModel;

    @Inject
    public SearchPresenter(SearchModel mMvpModel) {
        super(mMvpModel);
    }
}
