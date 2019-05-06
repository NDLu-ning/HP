package com.graduation.hp.ui.navigation.constitution;

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
import com.graduation.hp.repository.http.entity.local.ChannelVO;
import com.graduation.hp.repository.http.entity.local.ConstitutionVO;
import com.graduation.hp.ui.navigation.constitution.list.ConstitutionListFragment;
import com.graduation.hp.ui.navigation.news.list.NewsListFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

public class ConstitutionTabFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.constitution_pager_vg)
    ViewPager mViewPager;

    @BindView(R.id.constitution_tab_tl)
    TabLayout mTabLayout;

    @BindView(R.id.constitution_refresh_srl)
    SmartRefreshLayout mRefreshLayout;

    private ConstitutionTabAdapter mAdapter;

    public static ConstitutionTabFragment newInstance() {
        ConstitutionTabFragment fragment = new ConstitutionTabFragment();
        return fragment;
    }


    @Override
    protected void init(Bundle savedInstanceState, View view) {
        mAdapter = new ConstitutionTabAdapter(getChildFragmentManager(), Key.CONSTITUTIONS_CHANNELS);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.md_grey_666), getResources().getColor(R.color.colorPrimary));
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_constitution;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    static class ConstitutionTabAdapter extends FragmentPagerAdapter {

        private final ConstitutionVO[] channelVos;
        private ConstitutionListFragment[] mFragments;
        private int curPosition;

        ConstitutionTabAdapter(FragmentManager fm, ConstitutionVO[] channelVos) {
            super(fm);
//        this.token = token;
            this.channelVos = channelVos;
            this.mFragments = new ConstitutionListFragment[channelVos.length];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return channelVos[position].getType();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            curPosition = position;
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments[position] == null) {
                mFragments[position] = ConstitutionListFragment.newInstance(position, channelVos[position]);
            }
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return this.channelVos.length;
        }

        ConstitutionListFragment getCurrentItem() {
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
}
