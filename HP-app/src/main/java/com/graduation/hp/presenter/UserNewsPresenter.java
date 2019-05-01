package com.graduation.hp.presenter;

import android.util.Log;

import com.graduation.hp.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.contact.UserNewsContact;
import com.graduation.hp.repository.http.entity.NewsList;
import com.graduation.hp.repository.http.entity.Pager;
import com.graduation.hp.repository.model.impl.NewsModel;
import com.graduation.hp.ui.navigation.user.center.UserNewsFragment;
import com.graduation.hp.utils.BeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserNewsPresenter extends BasePresenter<UserNewsFragment, NewsModel>
        implements UserNewsContact.Presenter {

    private Page page;


    @Inject
    public UserNewsPresenter(NewsModel mMvpModel) {
        super(mMvpModel);
        page = new Page();
    }

    @Override
    public void downloadInitialNewsList(long userId) {
        mMvpView.showLoading();
        loadMoreNewsList(State.STATE_INIT, userId);
    }

    @Override
    public void loadMoreNewsList(State state, long userId) {
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
                    List<NewsList> list = new ArrayList<>();
                    for (int i = 0; i < page.getLimit(); i++) {
                        list.add(BeanFactory.createNewsList());
                    }
                    Log.d("TAG", "list.size():" + list.size());
                    mMvpView.onGetNewsListSuccess(list);
                    mMvpView.showMain();
                }));
//        mMvpModel.addSubscribe(mMvpModel.getNewsListByCategory(category, pager.getPage(), pager.getCount())
//                .doFinally(newsLists -> {
//
//                })
//                .subscribe(newsList -> {
//                    if (newsList != null && newsList.size() > 0) {
//                        mMvpView.onDownloadDataSuccess(refresh, newsList);
//                        mMvpView.showMain();
//                    } else {
//                    }
//                }, throwable -> {
//                    handlerApiError(throwable);
//                    mMvpView.showError(HPApplication.getStringById(R.string.tips_error_general));
//                }));
    }
}
