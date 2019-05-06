package com.graduation.hp.presenter;

import com.graduation.hp.R;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.repository.contact.InvitationDetailContact;
import com.graduation.hp.repository.model.impl.AttentionModel;
import com.graduation.hp.repository.model.impl.CommentModel;
import com.graduation.hp.repository.model.impl.InvitationModel;
import com.graduation.hp.repository.model.impl.LikeModel;
import com.graduation.hp.ui.navigation.constitution.detail.InvitationDetailFragment;

import javax.inject.Inject;

public class InvitationDetailPresenter extends BasePresenter<InvitationDetailFragment, InvitationModel>
        implements InvitationDetailContact.Presenter {

    @Inject
    AttentionModel attentionModel;

    @Inject
    LikeModel likeModel;

    @Inject
    CommentModel commentModel;

    @Inject
    public InvitationDetailPresenter(InvitationModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void focusOnAuthor(long userId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(attentionModel.focusUser(userId)
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
                        }));
    }

    @Override
    public void likeInvitation(long mInvitationId) {

    }

    @Override
    public void addComment(long invitationId, String content, long talkerUserId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(commentModel.discuss(invitationId, content,
                talkerUserId == -1L ? CommentModel.DISCUSS_COMMENT : CommentModel.DISCUSS_REPLY, talkerUserId)
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(success -> mMvpView.operateArticleCommentStatus(true),
                        throwable -> {
                            mMvpView.operateArticleCommentStatus(false);
                            handlerApiError(throwable);
                        }));
    }

    @Override
    public void isFocusOn(long userId) {
        mMvpModel.addSubscribe(attentionModel.isFocusOn(userId)
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
    public void getInvitationDetailById(long invitationId) {
//        mMvpView.showLoading();
//        mMvpModel.addSubscribe(mMvpModel.getNewsById(newsId)
//                .subscribe(articles -> {
//                    mMvpView.onGetNewsDetailInfoSuccess(articles);
//                }, throwable -> handlerApiError(throwable)));
    }
}
