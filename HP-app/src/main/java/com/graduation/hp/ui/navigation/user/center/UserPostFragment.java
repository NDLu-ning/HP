package com.graduation.hp.ui.navigation.user.center;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.UserPostPresenter;
import com.graduation.hp.repository.contact.UserPostContact;
import com.graduation.hp.repository.http.entity.PostItem;
import com.graduation.hp.ui.provider.UserPostItemProvider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class UserPostFragment extends RootFragment<UserPostPresenter>
        implements UserPostContact.View, OnLoadMoreListener, OnRefreshListener {


    public static UserPostFragment newInstance(long userId) {
        UserPostFragment fragment = new UserPostFragment();
        Bundle args = new Bundle();
        args.putLong(Key.USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    MultiTypeAdapter mAdapter;

    @Inject
    Items mItems;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    private RefreshLayout mRefreshLayout;

    private long mUserId;

    private List<PostItem> mList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
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
        mAdapter.register(PostItem.class, new UserPostItemProvider(listener));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onLazyLoad() {
        mPresenter.downloadInitialPostList(mUserId);
    }

    private final UserCenterTabListener listener = new UserCenterTabListener() {

        @Override
        public void onItemClick(boolean post, long id) {

        }

        @Override
        public void onLikeClick(boolean post, long id, boolean liked) {

        }
    };

    @Override
    protected boolean shouldShowNoDataView() {
        return true;
    }

    @Override
    protected int getNoDataStringResId() {
        return R.string.tips_empty_post_list;
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
    public void onGetPostListSuccess(List<PostItem> list) {
        if (mPresenter.isRefresh()) {
            mList.clear();
        }
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
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
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.loadMorePostList(State.STATE_MORE, mUserId);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.loadMorePostList(State.STATE_REFRESH, mUserId);
    }
}
