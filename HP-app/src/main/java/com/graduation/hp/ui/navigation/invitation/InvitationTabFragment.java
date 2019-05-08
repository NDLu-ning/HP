package com.graduation.hp.ui.navigation.invitation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.app.listener.SimpleItemClickListenerAdapter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.core.utils.ToastUtils;
import com.graduation.hp.presenter.InvitationTabPresenter;
import com.graduation.hp.repository.contact.InvitationTabContact;
import com.graduation.hp.repository.http.entity.vo.InvitationVO;
import com.graduation.hp.ui.auth.AuthActivity;
import com.graduation.hp.ui.navigation.constitution.detail.InvitationDetailActivity;
import com.graduation.hp.ui.navigation.invitation.create.InvitationCreationActivity;
import com.graduation.hp.ui.provider.PostItemProvider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class InvitationTabFragment extends RootFragment<InvitationTabPresenter>
        implements InvitationTabContact.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.view_base_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Inject
    RxPermissions rxPermissions;

    @Inject
    MultiTypeAdapter mAdapter;

    @Inject
    Items mItems;

    public static InvitationTabFragment newInstance() {
        InvitationTabFragment fragment = new InvitationTabFragment();
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        rootView.findViewById(R.id.post_publish_fab).setOnClickListener(v -> {
            if (mPresenter.checkUserLoginStatus()) {
                startActivity(InvitationCreationActivity.createIntent(getContext()));
            } else {
                ToastUtils.show(getContext(), getString(R.string.tips_please_login_first));
                startActivity(AuthActivity.createIntent(getContext()));
            }
        });
        initToolbar(rootView, getString(R.string.tab_post), 0);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        initMultiTypeAdapter();
    }

    private void initMultiTypeAdapter() {
        mAdapter.register(InvitationVO.class, new PostItemProvider(onItemClickListener));
        mAdapter.setItems(mItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onLazyLoad() {
        mPresenter.downloadInitialData();
    }

    private OnItemClickListener onItemClickListener = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, Object object) {
            InvitationVO invitationVO = (InvitationVO) object;
            startActivity(InvitationDetailActivity.createIntent(getContext(), invitationVO.getId()));
        }

        @Override
        public void OnItemCheckChange(View view, Object object, boolean isChecked) {
            InvitationVO invitationVO = (InvitationVO) object;
            mPresenter.likeArticle(invitationVO.getId());
        }
    };


    @Override
    protected boolean shouldShowNoDataView() {
        return false;
    }

    @Override
    protected int getNoDataStringResId() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_post;
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
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        mPresenter.downloadMoreData(State.STATE_MORE);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        mPresenter.downloadMoreData(State.STATE_REFRESH);
    }

    @Override
    public void onDownloadDataSuccess(State state, List<InvitationVO> newsLists) {
        if (!isAdded()) return;
        if (mPresenter.isRefresh()) {
            mItems.clear();
        }
        mItems.addAll(newsLists);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void operateLikeStateSuccess(boolean isLiked) {
        showMessage(getString(isLiked ? R.string.tips_like_success : R.string.tips_cancel_like_success));
    }

    @Override
    public void operateLikeStateError() {

    }

    @Override
    protected void onRetryClick() {
        mPresenter.downloadInitialData();
    }

    @Override
    protected void onEmptyClick() {
        mPresenter.downloadInitialData();
    }
}