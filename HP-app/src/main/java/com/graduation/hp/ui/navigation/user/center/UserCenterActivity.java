package com.graduation.hp.ui.navigation.user.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseActivity;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.presenter.UserCenterPresenter;
import com.graduation.hp.repository.contact.UserCenterContact;
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.repository.http.entity.local.ChannelVo;
import com.graduation.hp.repository.http.entity.local.UserVo;
import com.graduation.hp.ui.navigation.news.NewsTabFragment;
import com.graduation.hp.ui.navigation.news.list.NewsListFragment;
import com.graduation.hp.ui.navigation.user.info.UserInfoActivity;
import com.graduation.hp.widget.AttentionButton;
import com.graduation.hp.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.Body;

public class UserCenterActivity extends BaseActivity<UserCenterPresenter>
        implements UserCenterContact.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.user_center_arrow_iv)
    TriangleView mTriangleView;

    @BindView(R.id.user_center_summary_tv)
    AppCompatTextView mUserCenterSummaryTv;

    @BindView(R.id.user_center_icon_iv)
    AppCompatImageView mUserCenterIconIv;

    @BindView(R.id.user_center_edit_tv)
    AppCompatTextView mUserCenterEditTv;

    @BindView(R.id.user_center_sub_cb)
    AttentionButton mUserCenterSubCb;

    @BindView(R.id.user_center_attention_tv)
    AppCompatTextView mUserCenterAttentionTv;

    @BindView(R.id.user_center_collapsing_tl)
    CollapsingToolbarLayout mUserCenterCollapsingTl;

    @BindView(R.id.view_base_refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.user_center_pager_vp)
    ViewPager mViewPager;

    @BindView(R.id.user_center_tab_tl)
    TabLayout mTabLayout;

    private long mUserId;
    private long mOwnerId;
    private UserCenterAdapter mAdapter;

    public static Intent createIntent(Context context, long ownerId) {
        return createIntent(context, ownerId, ownerId);
    }

    public static Intent createIntent(Context context, long ownerId, long userId) {
        Intent intent = new Intent(context, UserCenterActivity.class);
        intent.putExtra(Key.OWNER_ID, ownerId);
        intent.putExtra(Key.USER_ID, userId);
        return intent;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mUserId = savedInstanceState.getLong(Key.USER_ID, -1L);
            mOwnerId = savedInstanceState.getLong(Key.OWNER_ID, -1L);
        } else {
            Intent intent = getIntent();
            mUserId = intent.getLongExtra(Key.USER_ID, -1L);
            mOwnerId = intent.getLongExtra(Key.OWNER_ID, -1L);
        }
        if (mUserId == -1L) {
            throw new IllegalArgumentException("You must to pass user's id into UserCenterActivity");
        }
        initToolbar(mRootView, "", R.mipmap.ic_navigation_back);
        initTabLayout();
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
    }

    private void initTabLayout() {
        mAdapter = new UserCenterAdapter(getSupportFragmentManager(), mUserId,
                getResources().getStringArray(R.array.user_center_tab_titles));
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.md_grey_666), getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onGetUserInfo(mUserId);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_user_center;
    }

    @Override
    public void onGetUserSuccess(UserVo userVo) {
        User user = userVo.getUser();
        setUserData(user);
        setUserAttentionNumber(userVo.getAttentionCount());
    }

    private void setUserAttentionNumber(long attentionCount) {
        mUserCenterAttentionTv.setVisibility(View.VISIBLE);
        mUserCenterAttentionTv.setText(getString(R.string.tips_total_attention_template
                , Integer.parseInt(attentionCount + "")));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setUserData(User user) {
        GlideUtils.loadUserHead(mUserCenterIconIv, user.getHeadUrl());
        if (mOwnerId == mUserId) {
            mUserCenterSubCb.setVisibility(View.GONE);
            mUserCenterEditTv.setVisibility(View.VISIBLE);
            mUserCenterEditTv.setOnClickListener(v -> startActivity(UserInfoActivity.createIntent(this)));
        } else {
            mUserCenterEditTv.setVisibility(View.GONE);
            mUserCenterSubCb.setVisibility(View.VISIBLE);
            mUserCenterSubCb.setAttentionButtonClickListener((compoundButton, checked) -> mPresenter.attentionUser(mOwnerId));
        }
        mUserCenterSummaryTv.setText(user.getRemark());
        mUserCenterCollapsingTl.setTitle(user.getNickname());
        mTriangleView.setTriangleViewClickListener((v, down) -> {
            int maxLine = mUserCenterSummaryTv.getMaxLines();
            mUserCenterSummaryTv.setMaxLines(maxLine > 1 ? 1 : 4);
        });
    }

    @OnClick({R.id.user_center_edit_tv})
    public void onClick(View view) {
        startActivity(UserInfoActivity.createIntent(this));
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        finish();
    }

    static class UserCenterAdapter extends FragmentPagerAdapter {

        private final long mUserId;
        private final String[] mTitles;
        private Fragment[] mFragments;

        private int curPosition;

        UserCenterAdapter(FragmentManager fm, long userId, String[] titles) {
            super(fm);
            this.mUserId = userId;
            this.mTitles = titles;
            this.mFragments = new Fragment[2];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            curPosition = position;
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (mFragments[position] == null) {
                        mFragments[position] = UserNewsFragment.newInstance(mUserId);
                    }
                    break;
                case 1:
                    if (mFragments[position] == null) {
                        mFragments[position] = UserPostFragment.newInstance(mUserId);
                    }
                    break;
            }
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return this.mTitles.length;
        }

        public Fragment getCurrentItem() {
            if (mFragments != null && mFragments.length > 0) {
                LogUtils.d("Position:" + curPosition);
                return mFragments[curPosition];
            }
            return null;
        }
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        ((OnLoadMoreListener) mAdapter.getCurrentItem()).onLoadMore(refreshLayout);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        ((OnRefreshListener) mAdapter.getCurrentItem()).onRefresh(refreshLayout);
    }

}
