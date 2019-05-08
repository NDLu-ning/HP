package com.graduation.hp.presenter;

import android.os.Bundle;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.repository.contact.UserInvitationContact;
import com.graduation.hp.repository.model.impl.InvitationModel;
import com.graduation.hp.repository.model.impl.LikeModel;
import com.graduation.hp.ui.navigation.user.center.UserInvitationFragment;

import javax.inject.Inject;

public class UserInvitationPresenter extends BasePresenter<UserInvitationFragment, InvitationModel>
        implements UserInvitationContact.Presenter {

    Page page;
    @Inject
    LikeModel likeModel;

    @Inject
    public UserInvitationPresenter(InvitationModel mMvpModel) {
        super(mMvpModel);
        page = new Page();
    }


    @Override
    public void downloadInitialPostList(long userId) {
        mMvpView.showLoading();
        loadMorePostList(State.STATE_INIT, userId);
    }

    @Override
    public void loadMorePostList(State state, long userId) {
        setCurState(state);
        if (isRefresh()) {
            page = new Page();
        }
        if (state == State.STATE_INIT) {
            mMvpView.showLoading();
        }
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.getInvitationListByUserId(userId, page.getOffset(), page.getLimit())
                .doOnSuccess(newsList -> page.setOffset(page.getOffset() + page.getLimit()))
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(newsList -> {
                    mMvpView.onGetDataSuccess(state, newsList);
                    mMvpView.showMain();
                }, throwable -> handlerApiError(throwable)));
    }

    @Override
    public void likeInvitation(long id) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(likeModel.likeInvitation(id)
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
