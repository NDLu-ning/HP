package com.graduation.hp.ui.navigation.attention;

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
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.AttentionTabPresenter;
import com.graduation.hp.repository.contact.AttentionTabContact;
import com.graduation.hp.repository.http.entity.FocusPO;
import com.graduation.hp.repository.preferences.PreferencesHelperImpl;
import com.graduation.hp.ui.navigation.user.center.UserCenterActivity;
import com.graduation.hp.ui.provider.AttentionItemProvider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Ning on 2019/4/25.
 */

public class AttentionTabFragment extends RootFragment<AttentionTabPresenter>
        implements AttentionTabContact.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.view_base_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Inject
    MultiTypeAdapter mAdapter;

    @Inject
    Items mItems;

    public static AttentionTabFragment newInstance() {
        AttentionTabFragment fragment = new AttentionTabFragment();
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        super.init(savedInstanceState, view);
        initMultiTypeAdapter();
    }

    private void initMultiTypeAdapter() {
        mAdapter.setItems(mItems);
        mAdapter.register(FocusPO.class, new AttentionItemProvider(mListener));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
    }

    private AttentionItemProvider.AttentionItemClickListener mListener = new AttentionItemProvider.AttentionItemClickListener() {
        @Override
        public void onAttentionCbClick(long authorId, boolean focusOn) {

        }

        @Override
        public void onItemClick(long authorId) {
            if (!isAdded()) return;
            long userId = PreferencesHelperImpl.getInstance().getCurrentUserId();
            getActivity().startActivity(UserCenterActivity.createIntent(getContext(), userId, authorId));
        }
    };


    @Override
    protected void onLazyLoad() {
        mPresenter.initialAttentionList();
    }

    @Override
    protected boolean shouldShowNoDataView() {
        return true;
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
    protected int getNoDataStringResId() {
        return R.string.tips_empty_attention_list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attention;
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
    public void onDownloadDataSuccess(List<FocusPO> newsLists) {
        if (mPresenter.isRefresh()) {
            mItems.clear();
        }
        mItems.addAll(newsLists);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRetryClick() {
        mPresenter.initialAttentionList();
    }
}
