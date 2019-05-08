package com.graduation.hp.ui.navigation.article.list;

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
import com.graduation.hp.core.app.listener.SimpleItemClickListenerAdapter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.presenter.ArticleListPresenter;
import com.graduation.hp.repository.contact.ArticleListContact;
import com.graduation.hp.repository.http.entity.vo.ArticleVO;
import com.graduation.hp.repository.http.entity.wrapper.ChannelVO;
import com.graduation.hp.ui.navigation.article.detail.ArticleDetailActivity;
import com.graduation.hp.ui.provider.NewsItemBigProvider;
import com.graduation.hp.ui.provider.NewsItemMultiProvider;
import com.graduation.hp.ui.provider.NewsItemSingleProvider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class ArticleListFragment extends RootFragment<ArticleListPresenter>
        implements ArticleListContact.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Inject
    MultiTypeAdapter mAdapter;

    @Inject
    Items mItems;

    private RefreshLayout mRefreshLayout;

    private ChannelVO mChannelVo;
    private int position;

    public static ArticleListFragment newInstance(int position, ChannelVO channel) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putInt(Key.POSITION, position);
        args.putParcelable(Key.CHANNEL, channel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        Bundle args = getArguments();
        position = args.getInt(Key.POSITION, 0);
        mChannelVo = args.getParcelable(Key.CHANNEL);
        if (mChannelVo == null) {
            throw new IllegalArgumentException("You must pass the parameter that indicate the channel of news");
        }
        initMultiTypeAdapter();
    }

    @Override
    protected void onLazyLoad() {
        if (mPresenter != null) {
            mPresenter.downloadInitialData(mChannelVo.getId());
        }
    }

    private void initMultiTypeAdapter() {
        mAdapter.register(ArticleVO.class).to(
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
        mAdapter.setItems(mItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
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
    public void onDownloadDataSuccess(List<ArticleVO> newsList) {
        if (mPresenter.isRefresh()) {
            mItems.clear();
        }
        mItems.addAll(newsList);
        LogUtils.d("mItems:" + mItems.size());
        mAdapter.notifyDataSetChanged();
    }

    private final SimpleItemClickListenerAdapter listener = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, Object object, int position) {
            ArticleVO articleVO = (ArticleVO) object;
            startActivity(ArticleDetailActivity.createIntent(getContext(), articleVO.getId()));
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
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
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.downloadMoreData(State.STATE_MORE, mChannelVo.getId());
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        if (mRefreshLayout == null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.downloadMoreData(State.STATE_REFRESH, mChannelVo.getId());
    }

    @Override
    protected boolean shouldShowNoDataView() {
        return true;
    }

    @Override
    protected int getNoDataStringResId() {
        return R.string.tips_empty_news_list;
    }
}
