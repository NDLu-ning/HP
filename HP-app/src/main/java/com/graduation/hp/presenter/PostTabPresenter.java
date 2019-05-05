package com.graduation.hp.presenter;

import android.text.TextUtils;

import com.graduation.hp.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.contact.PostTabContact;
import com.graduation.hp.repository.http.entity.PostItem;
import com.graduation.hp.repository.model.impl.PostModel;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.navigation.post.PostTabFragment;
import com.graduation.hp.utils.BeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;

public class PostTabPresenter extends BasePresenter<PostTabFragment, PostModel>
        implements PostTabContact.Presenter {

    @Inject
    UserModel userModel;

    private Page page;

    @Inject
    public PostTabPresenter(PostModel mMvpModel) {
        super(mMvpModel);
        this.page = new Page();
    }

    @Override
    public boolean checkUserLoginStatus() {
        RepositoryHelper repositoryHelper = userModel.getRepositoryHelper();
        return !TextUtils.isEmpty(repositoryHelper.getPreferencesHelper().getCurrentUserToken());
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
        mMvpModel.addSubscribe(Single.timer(2000L, TimeUnit.MILLISECONDS)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(aLong -> {
                    mMvpView.dismissDialog();
                    List<PostItem> list = new ArrayList<>();
                    for (int i = 0; i < page.getLimit(); i++) {
                        list.add(BeanFactory.createPostItem());
                    }
                    mMvpView.onDownloadDataSuccess(list);
                    mMvpView.showMain();
                }));
    }
}
