package com.graduation.hp.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.contact.InvitationTabContact;
import com.graduation.hp.repository.model.impl.InvitationModel;
import com.graduation.hp.repository.model.impl.LikeModel;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.navigation.invitation.InvitationTabFragment;

import javax.inject.Inject;

import io.reactivex.Single;

public class InvitationTabPresenter extends BasePresenter<InvitationTabFragment, InvitationModel>
        implements InvitationTabContact.Presenter {

    @Inject
    UserModel userModel;

    @Inject
    LikeModel likeModel;
    private Page page;

    @Inject
    public InvitationTabPresenter(InvitationModel mMvpModel) {
        super(mMvpModel);
        this.page = new Page();
    }

    @Override
    public boolean checkUserLoginStatus() {
        RepositoryHelper repositoryHelper = userModel.getRepositoryHelper();
        return !TextUtils.isEmpty(repositoryHelper.getPreferencesHelper().getCurrentUserToken());
    }

    @Override
    public long getUserPhysicalId() {
        RepositoryHelper repositoryHelper = userModel.getRepositoryHelper();
        return repositoryHelper.getPreferencesHelper().getCurrentUserPhysiquId();
    }

    @Override
    public void downloadInitialData() {
        mMvpView.showLoading();
        downloadMoreData(State.STATE_INIT);
    }

    @Override
    public void downloadMoreData(State state) {
        setCurState(state);
        if (isRefresh()) {
            page = new Page();
        }
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        if (state == State.STATE_INIT) {
            mMvpView.showLoading();
        }
        mMvpModel.addSubscribe(Single.just(getUserPhysicalId()).flatMap(physicalId -> {
                    if (physicalId > 0) {
                        return mMvpModel.getInvitationListByPhysiqueId(physicalId, page.getOffset(), page.getLimit());
                    } else {
                        return mMvpModel.getInvitationList(page.getOffset(), page.getLimit());
                    }
                }).doOnSuccess(invitationLists -> page.setOffset(page.getOffset() + page.getLimit()))
                        .doFinally(() -> mMvpView.dismissDialog())
                        .subscribe(invitationLists -> {
                            mMvpView.onDownloadDataSuccess(state, invitationLists);
                            mMvpView.showMain();
                        }, throwable -> handlerApiError(throwable))
        );
    }

    @Override
    public void likeInvitation(long mNewsId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(likeModel.likeInvitation(mNewsId)
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
