package com.graduation.hp.ui.navigation.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.repository.http.entity.wrapper.ChannelVO;
import com.graduation.hp.ui.navigation.news.list.NewsListFragment;
import com.graduation.hp.ui.search.SearchActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ning on 2019/4/25.
 */

public class NewsTabFragment extends BaseFragment
        implements OnLoadMoreListener, OnRefreshListener {

    @BindView(R.id.news_pager_vg)
    ViewPager mViewPager;

    @BindView(R.id.news_tab_tl)
    TabLayout mTabLayout;

    @BindView(R.id.news_refresh_srl)
    SmartRefreshLayout mRefreshLayout;

    private NewsTabAdapter mAdapter;

    public static NewsTabFragment newInstance() {
        NewsTabFragment fragment = new NewsTabFragment();
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        mAdapter = new NewsTabAdapter(getChildFragmentManager(), Key.DEFAULT_CHANNELS);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.md_grey_666), getResources().getColor(R.color.colorPrimary));
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }


    public static class NewsTabAdapter extends FragmentPagerAdapter {

        private final ChannelVO[] channelVos;
        private NewsListFragment[] mFragments;
        private int curPosition;

        public NewsTabAdapter(FragmentManager fm, ChannelVO[] channelVos) {
            super(fm);
            this.channelVos = channelVos;
            this.mFragments = new NewsListFragment[channelVos.length];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return channelVos[position].getTitile();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            curPosition = position;
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments[position] == null) {
                mFragments[position] = NewsListFragment.newInstance(position, channelVos[position]);
            }
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return this.channelVos.length;
        }

        public NewsListFragment getCurrentItem() {
            if (mFragments != null && mFragments.length > 0) {
                LogUtils.d("Position:" + curPosition);
                return mFragments[curPosition];
            }
            return null;
        }
    }

    @OnClick(R.id.news_search_rl)
    public void wantToSearch(View view) {
        getActivity().startActivity(SearchActivity.createIntent(getContext()));
        getActivity().overridePendingTransition(R.anim.slide_in_to_left, R.anim.slide_out_to_left);
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        mAdapter.getCurrentItem().onLoadMore(refreshLayout);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mAdapter.getCurrentItem().onRefresh(refreshLayout);
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }
}
