package com.graduation.hp.presenter;

import com.graduation.hp.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.repository.contact.AttentionTabContact;
import com.graduation.hp.repository.http.entity.FocusPO;
import com.graduation.hp.repository.model.impl.AttentionModel;
import com.graduation.hp.ui.navigation.attention.AttentionTabFragment;

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
                .doOnSuccess(list -> {
                    if (list != null && list.size() > 0) {
                        FocusPO item = list.get(0);
                        page.setOffset(item.getOffset());
                        page.setCreateTimeEnd(item.getCreateTimeEnd());
                        page.setCreateTimeStart(item.getCreateTimeStart());
                    }
                })
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(focusItems -> {
                    mMvpView.onDownloadDataSuccess(focusItems);
                    mMvpView.showMain();
                }, throwable -> handlerApiError(throwable)));
    }


}
