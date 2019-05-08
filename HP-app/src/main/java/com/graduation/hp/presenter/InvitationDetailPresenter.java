package com.graduation.hp.presenter;

import com.graduation.hp.R;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.repository.contact.InvitationDetailContact;
import com.graduation.hp.repository.model.impl.AttentionModel;
import com.graduation.hp.repository.model.impl.DiscussModel;
import com.graduation.hp.repository.model.impl.InvitationModel;
import com.graduation.hp.repository.model.impl.LikeModel;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.navigation.constitution.detail.InvitationDetailFragment;

import javax.inject.Inject;

public class InvitationDetailPresenter extends BasePresenter<InvitationDetailFragment, InvitationModel>
        implements InvitationDetailContact.Presenter {

    @Inject
    AttentionModel attentionModel;

    @Inject
    LikeModel likeModel;

    @Inject
    DiscussModel discussModel;

    @Inject
    UserModel userModel;

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
    public void likeInvitation(long invitationId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(likeModel.like(invitationId)
                .subscribe(
                        isLiked -> mMvpView.operateLikeStateSuccess(isLiked),
                        throwable -> {
                            mMvpView.operateLikeStateError();
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
    public void addComment(long invitationId, String content, long talkerUserId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(discussModel.discussInvitation(invitationId, content,
                talkerUserId <= 0 ? DiscussModel.DISCUSS_COMMENT : DiscussModel.DISCUSS_REPLY, talkerUserId)
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
    public void getInvitationDetail(long invitationId) {
        mMvpView.showLoading();
        mMvpModel.addSubscribe(mMvpModel.getInvitationById(invitationId)
                .subscribe(articles -> {
                    mMvpView.onGetInvitationDetailInfoSuccess(articles);
                    mMvpView.showMain();
                }, throwable -> handlerApiError(throwable)));
    }

    @Override
    public void getInvitationDiscuss(long invitationId) {
        mMvpModel.addSubscribe(discussModel.getInvitationDiscuss(invitationId)
                .subscribe(discussPOS -> {
                    mMvpView.onGetInvitationDiscussSuccess(discussPOS);
                    mMvpView.showMain();
                }, throwable -> {
                    if (throwable instanceof ApiException) {
                        ApiException exception = (ApiException) throwable;
                        if (exception.getCode() == ResponseCode.DATA_EMPTY.getStatus()) {
                            mMvpView.onGetInvitationDiscussEmpty();
                            return;
                        }
                    }
                    handlerApiError(throwable);
                }));
    }
}
