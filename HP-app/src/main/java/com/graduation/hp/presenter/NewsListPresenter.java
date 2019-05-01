package com.graduation.hp.presenter;

import android.util.Log;

import com.graduation.hp.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.contact.NewsListContact;
import com.graduation.hp.repository.http.entity.NewsList;
import com.graduation.hp.repository.http.entity.Pager;
import com.graduation.hp.repository.model.impl.NewsModel;
import com.graduation.hp.ui.navigation.news.list.NewsListFragment;
import com.graduation.hp.utils.BeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsListPresenter extends BasePresenter<NewsListFragment, NewsModel>
        implements NewsListContact.Presenter {

    private Pager pager;

    @Inject
    public NewsListPresenter(NewsModel mMvpModel) {
        super(mMvpModel);
        this.pager = new Pager(1, 10);
    }

    @Override
    public void downloadInitialData(long category) {
        mMvpView.showLoading();
        downloadMoreData(State.STATE_INIT, category);
    }

    @Override
    public void downloadMoreData(State state, long category) {
        setCurState(state);
        if (isRefresh()) {
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
                    List<NewsList> list = new ArrayList<>();
                    for (int i = 0; i < pager.getCount(); i++) {
                        list.add(BeanFactory.createNewsList());
                    }
                    Log.d("TAG", "list.size():" + list.size());
                    mMvpView.onDownloadDataSuccess(list);
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
