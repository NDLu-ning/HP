package com.graduation.hp.ui.navigation.attention;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.app.listener.SimpleItemClickListenerAdapter;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.AttentionTabPresenter;
import com.graduation.hp.repository.http.entity.NewsList;
import com.graduation.hp.ui.provider.NewsItemBigProvider;
import com.graduation.hp.ui.provider.NewsItemMultiProvider;
import com.graduation.hp.ui.provider.NewsItemSingleProvider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Ning on 2019/4/25.
 */

public class AttentionTabFragment extends RootFragment<AttentionTabPresenter>
        implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.view_base_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Inject
    MultiTypeAdapter mAdapter;

    @Inject
    Items mItems;

    private String mUserId;

    public static AttentionTabFragment newInstance() {
        AttentionTabFragment fragment = new AttentionTabFragment();
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        if (savedInstanceState != null) {
            mUserId = savedInstanceState.getString(Key.USER_ID);
        }
        initMultiTypeAdapter();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(Key.USER_ID, mUserId); // TODO 加密
        super.onSaveInstanceState(outState);
    }

    private void initMultiTypeAdapter() {
        mAdapter.register(NewsList.class).to(
                new NewsItemSingleProvider(listener),
                new NewsItemMultiProvider(listener),
                new NewsItemBigProvider(listener)
        ).withClassLinker((position, newsList) -> {
            String image = newsList.getImages();
            if (!TextUtils.isEmpty(image)) {
                String[] images = image.split(",");
                if (images.length >= 3) {
                    if (position % 2 == 0) {
                        return NewsItemSingleProvider.class;
                    }
                    return NewsItemMultiProvider.class;
                }
            }
            if (position % 2 == 0) {
                return NewsItemSingleProvider.class;
            } else {
                return NewsItemBigProvider.class;
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onLazyLoad() {
//        mPresenter.
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
        return 0;
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

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {

    }

    private final OnItemClickListener listener = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, Object object) {
            if (object instanceof String) {
                // 关注与否
            } else if (object instanceof NewsList) {
                // TODO
                // 点击新闻
            }
        }
    };
}
