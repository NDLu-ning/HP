package com.graduation.hp.ui.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Ning on 2019/2/24.
 */
public class SearchResultFragment extends BaseFragment
        implements OnLoadMoreListener, OnRefreshListener {

    public interface SearchResultFragmentListener{

    }

    @Inject
    Items mItems;

    @Inject
    MultiTypeAdapter mMultiTypeAdapter;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;


    @BindView(R.id.view_base_refresh_layout)
    SmartRefreshLayout searchResultRefreshSrl;

    private String keyword;

    public static SearchResultFragment newInstance(String content) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Key.KEYWORD, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View mView) {
        super.init(savedInstanceState, mView);
        Bundle bundle = getArguments();
        keyword = bundle.getString(Key.KEYWORD, "");
        searchResultRefreshSrl.setOnLoadMoreListener(this);
        searchResultRefreshSrl.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_base_list;
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if(!isAdded()) return ;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }
}
