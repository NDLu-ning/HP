package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.repository.contact.NewsDetailContact;
import com.graduation.hp.repository.http.entity.ArticleVO;
import com.graduation.hp.repository.http.entity.local.ArticleVOWrapper;
import com.graduation.hp.repository.model.impl.AttentionModel;
import com.graduation.hp.repository.model.impl.NewsModel;
import com.graduation.hp.ui.navigation.news.detail.NewsDetailFragment;

import javax.inject.Inject;

import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class NewsDetailPresenter extends BasePresenter<NewsDetailFragment, NewsModel>
        implements NewsDetailContact.Presenter {

    @Inject
    AttentionModel attentionModel;


    @Inject
    public NewsDetailPresenter(NewsModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public int getLocalTextSize() {
        return 0;
    }

    @Override
    public void getNewsDetailByNewsId(long newsId) {
        mMvpView.showLoading();
        mMvpModel.addSubscribe(mMvpModel.getNewsById(newsId)
                .subscribe(articles -> {
                    mMvpView.onGetNewsDetailInfoSuccess(articles);
                }, throwable -> handlerApiError(throwable)));
    }

    @Override
    public void isFocusOn(long authorId) {
        attentionModel.addSubscribe(attentionModel.isFocusOn(authorId)
                .subscribe(isFocusOn -> {
                    mMvpView.onGetAttentionSuccess(isFocusOn);
                }, throwable -> {
                    if (throwable instanceof ApiException) {
                        ApiException apiException = (ApiException) throwable;
                        if (apiException.getCode() == ResponseCode.TOKEN_ERROR.getStatus()) {
                            mMvpView.onGetAttentionSuccess(false);
                            return;
                        }
                    }
                    handlerApiError(throwable);
                }));
    }

    @Override
    public void addComment(long newsId, String content) {

    }

    @Override
    public void likeArticle(long mNewsId, boolean liked) {

    }

    @Override
    public void focusOnAuthor(long authorId, boolean focusOn) {

    }
}
