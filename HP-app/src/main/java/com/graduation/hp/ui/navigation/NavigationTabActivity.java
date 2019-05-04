package com.graduation.hp.ui.navigation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.event.TokenInvalidEvent;
import com.graduation.hp.core.ui.BaseActivity;
import com.graduation.hp.core.widget.BaseViewPager;
import com.graduation.hp.core.widget.RegisteredFragmentPagerAdapter;
import com.graduation.hp.ui.auth.AuthActivity;
import com.graduation.hp.ui.navigation.attention.AttentionTabFragment;
import com.graduation.hp.ui.navigation.constitution.ConstitutionTabFragment;
import com.graduation.hp.ui.navigation.user.UserTabFragment;
import com.graduation.hp.ui.navigation.news.NewsTabFragment;
import com.graduation.hp.ui.navigation.post.PostTabFragment;
import com.graduation.hp.widget.NavigationTabView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Create by Ning on 2019/04/25
 */
public class NavigationTabActivity extends BaseActivity {

    @BindView(R.id.navigation_container_fl)
    BaseViewPager mViewPager;

    private static final int TAB_ID_NEWS = 100;
    private static final int TAB_ID_CONSTITUTION = 101;
    private static final int TAB_ID_ATTENTION = 102;
    private static final int TAB_ID_POST = 103;
    private static final int TAB_ID_MYSELF = 104;
    private boolean isCurUserLogin = false;
    private int mCurPage = 0;
    private int mSubTabId = 0;
    private NavigationPagerAdapter mAdapter;

    public static Intent createIntent(Context context, boolean isCurUserLogin) {
        final Intent intent = new Intent(context, NavigationTabActivity.class);
        intent.putExtra(Key.IS_CURRENT_USER_LOGIN, isCurUserLogin);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static Intent createIntent(Context context, boolean isCurUserLogin, Integer tabId) {
        final Intent intent = createIntent(context, isCurUserLogin);
        intent.putExtra(Key.PAGE, tabId);
        return intent;
    }

    public static Intent createIntent(Context context, boolean isCurUserLogin, int tabId, int subTabId) {
        final Intent intent = createIntent(context, isCurUserLogin, tabId);
        intent.putExtra(Key.SUB_TAB_ID, subTabId);
        return intent;
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_navigation_tab;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isCurUserLogin = savedInstanceState.getBoolean(Key.IS_CURRENT_USER_LOGIN, false);
            mSubTabId = savedInstanceState.getInt(Key.SUB_TAB_ID, 0);
        } else {
            Intent intent = getIntent();
            isCurUserLogin = intent.getBooleanExtra(Key.IS_CURRENT_USER_LOGIN, false);
            mSubTabId = intent.getIntExtra(Key.SUB_TAB_ID, 0);
        }
        mAdapter = new NavigationPagerAdapter(getSupportFragmentManager(), isCurUserLogin);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        TabLayout tabLayout = findViewById(R.id.navigation_bottom_bbl);
        tabLayout.setupWithViewPager(mViewPager);
        setUpTabCustomViews(tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((NavigationTabView) tab.getCustomView()).onTabSelected();
                final int tabPosition = tab.getPosition();
                mViewPager.setCurrentItem(tabPosition, true);
                setTitle(getTabTitleResId(mViewPager.getCurrentItem(), true));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((NavigationTabView) tab.getCustomView()).onTabUnselected();
                invalidateOptionsMenu();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                ((NavigationTabView) tab.getCustomView()).onTabSelected();
                final int currentPosition = mViewPager.getCurrentItem();
                if (currentPosition == tab.getPosition()) {
//                    scrollToTop(currentPosition);
                }
            }
        });
        final int startTabId = getIntent().getIntExtra(Key.PAGE, mAdapter.getTabAtPosition(0));
        final int startTabPosition = mAdapter.getPositionForTab(startTabId);
        tabLayout.getTabAt(startTabPosition).select();

    }

    private void scrollToTop(int currentPosition) {
        final Fragment fragment = mAdapter.getRegisteredFragment(currentPosition);
        if (fragment != null) {
            switch (mAdapter.getTabAtPosition(currentPosition)) {
                case TAB_ID_NEWS: {
                    if (fragment instanceof NewsTabFragment) {
//                        ((NewsTabFragment) fragment).scrollToTop();
                    }
                    break;
                }
                case TAB_ID_ATTENTION: {
                    if (fragment instanceof AttentionTabFragment) {
//                        ((AttentionTabFragment) fragment).scrollToTop();
                    }
                    break;
                }
//                case TAB_ID_MESSAGING: {
//                    if (fragment instanceof ChatRoomsFragment) {
//                        ((ChatRoomsFragment) fragment).scrollToTop();
//                    }
//                    break;
//                }
//                case TAB_ID_NOTIFICATIONS: {
//                    if (fragment instanceof NotificationTabFragment) {
//                        ((NotificationTabFragment) fragment).scrollToTop();
//                    }
//                    break;
//                }
//                case TAB_ID_MORE: {
//                    if (fragment instanceof BaseOverflowMenuFragment) {
//                        ((BaseOverflowMenuFragment) fragment).scrollToTop();
//                    }
//                    break;
//                }
//                case TAB_ID_WHATS_DUE: {
//                    if (fragment instanceof StudentToDoFragment) {
//                        ((StudentToDoFragment) fragment).scrollToTop();
//                    }
//                    break;
//                }
//                default: {
//                    throw new IllegalArgumentException("Invalid position: " + currentPosition);
//                }
            }
        }
    }

    private void setUpTabCustomViews(TabLayout tabLayout) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(
                    getCustomTabView(getTabIconResId(i), getTabTitleResId(i, false)));
        }
    }

    @NotNull
    private NavigationTabView getCustomTabView(int drawableResId, int nameResId) {
        final NavigationTabView tabView = new NavigationTabView(this);
        tabView.setIcon(drawableResId);
        tabView.setLabel(nameResId);
        return tabView;
    }

    private int getTabTitleResId(int position, boolean forActionBar) {
        switch (mAdapter.getTabAtPosition(position)) {
            case TAB_ID_NEWS:
                return R.string.tab_home;
            case TAB_ID_ATTENTION:
                return R.string.tab_attention;
            case TAB_ID_CONSTITUTION:
                return R.string.tab_constitution;
            case TAB_ID_POST:
                return R.string.tab_post;
            case TAB_ID_MYSELF:
                return R.string.tab_myself;
            default:
                throw new IllegalArgumentException("Unexpected Tab in NavigationPagerAdapter: "
                        + mAdapter.getTabAtPosition(position));
        }
    }

    private int getTabIconResId(int position) {
        switch (mAdapter.getTabAtPosition(position)) {
            case TAB_ID_NEWS:
                return R.drawable.selector_news_tab;
            case TAB_ID_ATTENTION:
                return R.drawable.selector_attention_tab;
            case TAB_ID_CONSTITUTION:
                return R.drawable.selector_constitution_tab;
            case TAB_ID_POST:
                return R.drawable.selector_post_tab;
            case TAB_ID_MYSELF:
                return R.drawable.selector_myself_tab;
            default:
                throw new IllegalArgumentException("Unexpected Tab in NavigationPagerAdapter: "
                        + mAdapter.getTabAtPosition(position));
        }
    }


    private class NavigationPagerAdapter extends RegisteredFragmentPagerAdapter {

        private final Integer[] mTabIds;
        private boolean curUserLogin;

        NavigationPagerAdapter(FragmentManager fm, boolean isCurUserLogin) {
            super(fm);
            this.curUserLogin = isCurUserLogin;
            if (curUserLogin) {
                mTabIds = new Integer[]{
                        TAB_ID_NEWS,
                        TAB_ID_ATTENTION,
                        TAB_ID_CONSTITUTION,
                        TAB_ID_POST,
                        TAB_ID_MYSELF,
                };
            } else {
                mTabIds = new Integer[]{
                        TAB_ID_NEWS,
                        TAB_ID_CONSTITUTION,
                        TAB_ID_POST,
                        TAB_ID_MYSELF,
                };
            }
        }

        @Override
        public Fragment getItem(int position) {
            switch (mTabIds[position]) {
                case TAB_ID_NEWS: {
                    final NewsTabFragment fragment = NewsTabFragment.newInstance();
                    return fragment;
                }
                case TAB_ID_ATTENTION: {
                    final AttentionTabFragment fragment = AttentionTabFragment.newInstance();
                    return fragment;
                }
                case TAB_ID_CONSTITUTION: {
                    final ConstitutionTabFragment fragment = ConstitutionTabFragment.newInstance();
                    return fragment;
                }
                case TAB_ID_POST: {
                    final PostTabFragment fragment = PostTabFragment.newInstance();
                    return fragment;
                }
                case TAB_ID_MYSELF: {
                    final UserTabFragment fragment = UserTabFragment.newInstance();
                    return fragment;
                }
                default: {
                    throw new IllegalArgumentException("Invalid position.");
                }
            }
        }

        @Override
        public int getCount() {
            return mTabIds.length;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_UNCHANGED;
        }

        int getPositionForTab(int tabId) {
            return Arrays.asList(mTabIds).indexOf(tabId);
        }

        int getTabAtPosition(int position) {
            return mTabIds[position];
        }
    }


    @Override
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void skipToLoginPage(TokenInvalidEvent event) {
        EventBus.getDefault().cancelEventDelivery(event);
        startActivity(AuthActivity.createIntent(this));
    }

    @Override
    public boolean useEventBus() {
        return true;
    }
}