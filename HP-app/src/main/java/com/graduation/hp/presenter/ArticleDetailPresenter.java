package com.graduation.hp.presenter;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.contact.ArticleDetailContact;
import com.graduation.hp.repository.model.impl.ArticleModel;
import com.graduation.hp.repository.model.impl.AttentionModel;
import com.graduation.hp.repository.model.impl.DiscussModel;
import com.graduation.hp.repository.model.impl.LikeModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;
import com.graduation.hp.ui.navigation.article.detail.ArticleDetailFragment;

import javax.inject.Inject;

public class ArticleDetailPresenter extends BasePresenter<ArticleDetailFragment, ArticleModel>
        implements ArticleDetailContact.Presenter {

    @Inject
    AttentionModel attentionModel;

    @Inject
    LikeModel likeModel;

    @Inject
    DiscussModel commentModel;

    @Inject
    public ArticleDetailPresenter(ArticleModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public int getLocalTextSize() {
        RepositoryHelper repositoryHelper = mMvpModel.getRepositoryHelper();
        if (repositoryHelper == null) {
            return 1;
        }
        PreferencesHelper preferencesHelper = repositoryHelper.getPreferencesHelper();
        if (preferencesHelper == null) {
            return 1;
        }
        return preferencesHelper.getTextSize();
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
        mMvpModel.addSubscribe(attentionModel.isFocusOn(authorId)
                .subscribe(
                        isFocusOn -> mMvpView.onGetAttentionSuccess(isFocusOn),
                        throwable -> {
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
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(commentModel.discuss(newsId, content, DiscussModel.DISCUSS_COMMENT, -1L)
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(success -> mMvpView.operateArticleCommentStatus(true),
                        throwable -> {
                            mMvpView.operateArticleCommentStatus(false);
                            handlerApiError(throwable);
                        }));
    }

    @Override
    public void likeArticle(long mNewsId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(likeModel.like(mNewsId)
                .subscribe(
                        isLiked -> mMvpView.operateLikeStateSuccess(isLiked),
                        throwable -> {
                            mMvpView.operateLikeStateError();
                            if (throwable instanceof ApiException) {
                                ApiException apiException = (ApiException) throwable;
                                if (apiException.getCode() == ResponseCode.TOKEN_ERROR.getStatus()) {
                                    mMvpView.showMessage(HPApplication.getStringById(R.string.tips_please_login_first));
                                    return;
                                } else if (apiException.getCode() == ResponseCode.NOT_NEED_SHOW_MESSAGE.getStatus()) {
                                    mMvpView.showMessage(HPApplication.getStringById(R.string.tips_happen_unknown_error));
                                    mMvpView.operateLikeStateError();
                                    return;
                                }
                            }
                            handlerApiError(throwable);
                        }));
    }

    @Override
    public void focusOnAuthor(long authorId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(attentionModel.focusUser(authorId)
                .subscribe(
                        isFocusOn -> mMvpView.operateAttentionStateSuccess(isFocusOn),
                        throwable -> {
                            if (throwable instanceof ApiException) {
                                ApiException apiException = (ApiException) throwable;
                                if (apiException.getCode() == ResponseCode.TOKEN_ERROR.getStatus()) {
                                    mMvpView.showMessage(HPApplication.getStringById(R.string.tips_please_login_first));
                                    return;
                                }
                            }
                            handlerApiError(throwable);
                        }));
    }

    @Override
    public long getLocalCurrentUserId() {
        PreferencesHelper preferencesHelper = getModel().getRepositoryHelper().getPreferencesHelper();
        return preferencesHelper.getCurrentUserId();
    }
}
