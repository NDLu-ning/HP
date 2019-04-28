package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.NewsDetailContact;
import com.graduation.hp.repository.model.impl.CollectModel;
import com.graduation.hp.repository.model.impl.NewsModel;
import com.graduation.hp.ui.navigation.news.detail.NewsDetailActivity;

import javax.inject.Inject;

public class NewsDetailPresenter extends BasePresenter<NewsDetailActivity, NewsModel>
        implements NewsDetailContact.Presenter {


    @Inject
    CollectModel collectModel;

    @Inject
    public NewsDetailPresenter(NewsModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getNewsDetailByNewsId(String newsId) {

    }

    @Override
    public void addNewsComment(String newsId, String content) {

    }
}
