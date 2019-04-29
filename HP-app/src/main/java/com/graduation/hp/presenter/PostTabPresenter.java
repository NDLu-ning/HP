package com.graduation.hp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.graduation.hp.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.contact.PostTabContact;
import com.graduation.hp.repository.http.entity.NewsList;
import com.graduation.hp.repository.http.entity.Pager;
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

    private Pager pager;

    @Inject
    public PostTabPresenter(PostModel mMvpModel) {
        super(mMvpModel);
        this.pager = new Pager(1, 10);
    }

    @Override
    public boolean checkUserLoginStatus() {
        RepositoryHelper repositoryHelper = userModel.getRepositoryHelper();
        return !TextUtils.isEmpty(repositoryHelper.getPreferencesHelper().getCurrentUserToken());
    }

    @Override
    public void downloadInitialData() {
        setCurRefreshError(false);
        mMvpView.showLoading();
        downloadMoreData(true);
    }

    @Override
    public void downloadMoreData(boolean refresh) {
        if (refresh) {
            pager = new Pager(1, 10);
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
                    for (int i = 0; i < pager.getCount(); i++) {
                        list.add(BeanFactory.createPostItem());
                    }
                    Log.d("TAG", "list.size():" + list.size());
                    mMvpView.onDownloadDataSuccess(refresh, list);
                    mMvpView.showMain();
                }));
    }
}
