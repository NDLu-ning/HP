package com.graduation.hp.ui.navigation.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.repository.http.entity.FavouriteChannel;
import com.graduation.hp.ui.navigation.news.list.NewsListFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

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


    private class NewsTabAdapter extends FragmentPagerAdapter {

        private final FavouriteChannel[] favouriteChannels;
        //    private final String token;
        private NewsListFragment[] mFragments;
        private int curPosition;

        public NewsTabAdapter(FragmentManager fm, FavouriteChannel[] favouriteChannels) {
            super(fm);
//        this.token = token;
            this.favouriteChannels = favouriteChannels;
            this.mFragments = new NewsListFragment[favouriteChannels.length];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return favouriteChannels[position].getChannel_name();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            curPosition = position;
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments[position] == null) {
                mFragments[position] = NewsListFragment.newInstance(position, favouriteChannels[position]);
            }
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return this.favouriteChannels.length;
        }

        public NewsListFragment getCurrentItem() {
            if (mFragments != null && mFragments.length > 0) {
                LogUtils.d("Position:" + curPosition);
                return mFragments[curPosition];
            }
            return null;
        }
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
