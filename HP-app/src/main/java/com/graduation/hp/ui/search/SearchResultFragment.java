package com.graduation.hp.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
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
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.SearchResultPresenter;
import com.graduation.hp.repository.contact.SearchResultContact;
import com.graduation.hp.repository.http.entity.vo.ArticleVO;
import com.graduation.hp.ui.navigation.article.detail.ArticleDetailActivity;
import com.graduation.hp.ui.provider.NewsItemBigProvider;
import com.graduation.hp.ui.provider.NewsItemMultiProvider;
import com.graduation.hp.ui.provider.NewsItemSingleProvider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Ning on 2019/2/24.
 */
public class SearchResultFragment extends RootFragment<SearchResultPresenter>
        implements SearchResultContact.View, OnLoadMoreListener, OnRefreshListener {

    private SearchResultFragmentListener mListener;

    public interface SearchResultFragmentListener {
        void startSearchPage(String keyword);
    }

    @Inject
    Items mItems;

    @Inject
    MultiTypeAdapter mAdapter;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @BindView(R.id.view_base_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.search_result_keyword_tv)
    AppCompatTextView searchResultKeywordTv;

    private String keyword;

    public static SearchResultFragment newInstance(String content) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Key.KEYWORD, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        try {
            mListener = (SearchResultFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement SearchResultFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        super.init(savedInstanceState, mView);
        if (savedInstanceState != null) {
            keyword = savedInstanceState.getString(Key.KEYWORD, "");
        } else {
            Bundle bundle = getArguments();
            keyword = bundle.getString(Key.KEYWORD, "");
        }
        if (TextUtils.isEmpty(keyword)) {
            throw new IllegalArgumentException("SearchResultFragment must be receive keyword");
        }
        searchResultKeywordTv.setText(keyword);
        initMultiTypeAdapter();
        initListener();
    }


    @Override
    protected void onLazyLoad() {
        mPresenter.searchKeywordNewsList(State.STATE_INIT, keyword);
    }

    @Override
    protected boolean shouldShowNoDataView() {
        return true;
    }

    @Override
    protected int getNoDataStringResId() {
        return R.string.tips_empty_search_result;
    }

    private void initListener() {
        mRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLayout.setOnRefreshListener(this);
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(Key.KEYWORD, keyword);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        mPresenter.searchKeywordNewsList(State.STATE_MORE, keyword);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        mPresenter.searchKeywordNewsList(State.STATE_REFRESH, keyword);
    }

    @OnClick(R.id.search_result_keyword_tv)
    public void onKeywordTvClick() {
        if (!isAdded()) return;
        mListener.startSearchPage(keyword);
    }

    @OnClick(R.id.search_result_clear_iv)
    public void onClearIvClick() {
        if (!isAdded()) return;
        mListener.startSearchPage("");
    }

    @OnClick(R.id.toolbar_left_btn)
    @Override
    public void onToolbarLeftClickListener(View view) {
        if (!isAdded()) return;
        ((SearchActivity) getActivity()).onToolbarLeftClickListener(view);
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    private OnItemClickListener listener = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, Object object, int position) {
            ArticleVO articleVO = (ArticleVO) object;
            startActivity(ArticleDetailActivity.createIntent(getContext(), articleVO.getId()));
        }
    };

    @Override
    public void onSearchKeywordSuccess(State state, List<ArticleVO> articleVOS) {
        if (mPresenter.isRefresh()) {
            mItems.clear();
        }
        mItems.addAll(articleVOS);
        mAdapter.notifyDataSetChanged();
    }
}
