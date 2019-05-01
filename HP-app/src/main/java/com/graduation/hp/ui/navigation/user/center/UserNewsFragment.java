package com.graduation.hp.ui.navigation.user.center;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.UserNewsPresenter;
import com.graduation.hp.repository.contact.UserNewsContact;
import com.graduation.hp.repository.http.entity.NewsList;
import com.graduation.hp.ui.provider.UserNewsItemProvider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class UserNewsFragment extends RootFragment<UserNewsPresenter>
        implements UserNewsContact.View, OnRefreshListener, OnLoadMoreListener {


    public static UserNewsFragment newInstance(long userId) {
        UserNewsFragment newsFragment = new UserNewsFragment();
        Bundle args = new Bundle();
        args.putLong(Key.USER_ID, userId);
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Inject
    MultiTypeAdapter mAdapter;

    @Inject
    Items mItems;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    private RefreshLayout mRefreshLayout;
    private long mUserId;

    @Override
    protected void init(Bundle savedInstanceState, android.view.View rootView) {
        super.init(savedInstanceState, rootView);
        initBasicData(savedInstanceState);
        initMultiTypeAdapter();
    }

    private void initBasicData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mUserId = savedInstanceState.getLong(Key.USER_ID, -1L);
        } else {
            Bundle bundle = getArguments();
            mUserId = bundle.getLong(Key.USER_ID, -1L);
        }
        if (mUserId == -1L) {
            throw new IllegalArgumentException("UserPostFragment must be passed user's id");
        }
    }

    private void initMultiTypeAdapter() {
        mAdapter.register(NewsList.class, new UserNewsItemProvider(listener));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onLazyLoad() {
        mPresenter.downloadInitialNewsList(mUserId);
    }

    private final UserCenterTabListener listener = new UserCenterTabListener() {

        @Override
        public void onLikeClick(boolean post, long id, boolean liked) {

        }

        @Override
        public void onItemClick(boolean post, long id) {

        }
    };

    @Override
    protected boolean shouldShowNoDataView() {
        return true;
    }

    @Override
    protected int getNoDataStringResId() {
        return R.string.tips_empty_news_list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_post;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void dismissDialog() {
        super.dismissDialog();
        if (mRefreshLayout != null && mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout != null && mRefreshLayout.getState() == RefreshState.Loading) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onGetNewsListSuccess(List<NewsList> list) {
        if (mPresenter.isRefresh()) {
            mItems.clear();
        }
        mItems.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.loadMoreNewsList(State.STATE_MORE, mUserId);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.loadMoreNewsList(State.STATE_REFRESH, mUserId);
    }


}