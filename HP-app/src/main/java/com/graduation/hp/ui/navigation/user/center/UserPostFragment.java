package com.graduation.hp.ui.navigation.user.center;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.app.listener.SimpleItemClickListenerAdapter;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.UserPostPresenter;
import com.graduation.hp.repository.contact.UserPostContact;
import com.graduation.hp.repository.http.entity.PostItem;
import com.graduation.hp.ui.provider.UserPostItemAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UserPostFragment extends RootFragment<UserPostPresenter>
        implements UserPostContact.View, XRecyclerView.LoadingListener {


    public static UserPostFragment newInstance(long userId) {
        UserPostFragment fragment = new UserPostFragment();
        Bundle args = new Bundle();
        args.putLong(Key.USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.view_main)
    XRecyclerView mRecyclerView;

    private UserPostItemAdapter mAdapter;
    private List<PostItem> mList = new ArrayList<>();
    private long mUserId;

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
        mAdapter = new UserPostItemAdapter(listener, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLoadingListener(this);
        mAdapter.notifyDataSetChanged();
    }

    private final UserPostItemAdapter.UserPostItemAdapterListener listener = new UserPostItemAdapter.UserPostItemAdapterListener() {

        @Override
        public void onLikeClick(long postId, boolean liked) {

        }

        @Override
        public void onPostClick(long postId) {

        }
    };

    @Override
    public void onDestroyView() {
        if (mRecyclerView != null) {
            mRecyclerView.destroy();
        }
        super.onDestroyView();
    }

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
    public void downloadInitialPostList(long userId) {
        mPresenter.downloadInitialPostList(userId);
    }

    @Override
    public void onGetPostListSuccess(boolean refresh, List<PostItem> list) {
        if (refresh) {
            mList.clear();
        }
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        if (!isAdded()) return;
        mPresenter.setCurRefreshError(true);
        mPresenter.loadMorePostList(true, mUserId);
    }

    @Override
    public void onLoadMore() {
        if (!isAdded()) return;
        mPresenter.setCurRefreshError(true);
        mPresenter.loadMorePostList(false, mUserId);
    }

    @Override
    public void dismissDialog() {
        super.dismissDialog();
        if (mRecyclerView != null) {
            mRecyclerView.refreshComplete();
            mRecyclerView.loadMoreComplete();
        }
    }
}
