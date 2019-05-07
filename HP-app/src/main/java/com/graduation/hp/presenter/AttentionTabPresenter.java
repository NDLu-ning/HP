package com.graduation.hp.presenter;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.app.event.TokenInvalidEvent;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.repository.contact.AttentionTabContact;
import com.graduation.hp.repository.model.impl.AttentionModel;
import com.graduation.hp.ui.navigation.attention.AttentionTabFragment;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

public class AttentionTabPresenter extends BasePresenter<AttentionTabFragment, AttentionModel>
        implements AttentionTabContact.Presenter {

    private Page page;

    @Inject
    public AttentionTabPresenter(AttentionModel mMvpModel) {
        super(mMvpModel);
        page = new Page();
    }

    @Override
    public void initialAttentionList() {
        mMvpView.showLoading();
        downloadMoreData(State.STATE_INIT);
    }

    @Override
    public void focusOnUser(long authorId) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(mMvpModel.focusUser(authorId)
                .subscribe(
                        isFocusOn -> mMvpView.operateAttentionStateSuccess(isFocusOn),
                        throwable -> {
                            if (throwable instanceof ApiException) {
                                ApiException apiException = (ApiException) throwable;
                                if (apiException.getCode() == ResponseCode.TOKEN_ERROR.getStatus()) {
                                    EventBus.getDefault().post(TokenInvalidEvent.INSTANCE);
                                    return;
                                }
                                mMvpView.showMessage(throwable.getMessage());
                            }
                        }));
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
        mMvpModel.addSubscribe(mMvpModel.getAttentionList(page.getOffset(), page.getLimit())
                .doOnSuccess(list -> page.setOffset(page.getOffset() + list.size()))
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(focusItems -> {
                    mMvpView.onDownloadDataSuccess(focusItems);
                    mMvpView.showMain();
                }, throwable -> handlerApiError(throwable)));
    }


}
