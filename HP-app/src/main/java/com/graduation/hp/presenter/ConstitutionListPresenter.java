package com.graduation.hp.presenter;

import android.os.Bundle;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.repository.contact.ConstitutionListContact;
import com.graduation.hp.repository.model.impl.InvitationModel;
import com.graduation.hp.ui.navigation.constitution.list.ConstitutionListFragment;

import javax.inject.Inject;

public class ConstitutionListPresenter extends BasePresenter<ConstitutionListFragment, InvitationModel>
        implements ConstitutionListContact.Presenter {

    private Page page;

    @Inject
    public ConstitutionListPresenter(InvitationModel mMvpModel) {
        super(mMvpModel);
        page = new Page();
    }


    @Override
    public void getConstitutionInvitationList(State state, long physiqueId) {
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
        mMvpModel.addSubscribe(mMvpModel.getInvitationListByPhysiqueId(physiqueId, page.getOffset(), page.getLimit())
                .doOnSuccess(newsList -> page.setOffset(page.getOffset() + page.getLimit()))
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(newsList -> {
                    mMvpView.onGetDataSuccess(state, newsList);
                    mMvpView.showMain();
                }, throwable -> handlerApiError(throwable)));
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
