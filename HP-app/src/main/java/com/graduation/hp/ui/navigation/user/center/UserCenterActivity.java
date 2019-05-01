package com.graduation.hp.ui.navigation.user.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.presenter.UserCenterPresenter;
import com.graduation.hp.repository.contact.UserCenterContact;
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.ui.navigation.user.info.UserInfoActivity;
import com.graduation.hp.widget.TriangleView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class UserCenterActivity extends SingleFragmentActivity<UserCenterPresenter>
        implements UserCenterContact.View{

    @BindView(R.id.user_center_arrow_iv)
    TriangleView mTriangleView;

    @BindView(R.id.user_center_summary_tv)
    AppCompatTextView mUserCenterSummaryTv;

    @BindView(R.id.user_center_icon_iv)
    AppCompatImageView mUserCenterIconIv;

    @BindView(R.id.user_center_edit_tv)
    AppCompatTextView mUserCenterEditTv;

    @BindView(R.id.user_center_sub_cb)
    AppCompatCheckBox mUserCenterSubCb;

    @BindView(R.id.user_center_attention_tv)
    AppCompatTextView mUserCenterAttentionTv;

    @BindView(R.id.user_center_collapsing_tl)
    CollapsingToolbarLayout mUserCenterCollapsingTl;

    private long mUserId;
    private long mOwnerId;

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
        super.initData(savedInstanceState);
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
    protected Fragment createMainContentFragment() {
        return UserPostFragment.newInstance(mUserId);
    }

    @Override
    public void onGetUserSuccess(User user) {
        setUserData(user);
        ((UserPostFragment)getMainContentFragment()).downloadInitialPostList(user.getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setUserData(User user) {
        GlideUtils.loadUserHead(mUserCenterIconIv, user.getHeadUrl());
        if (mOwnerId == mUserId) {
            mUserCenterEditTv.setVisibility(View.VISIBLE);
            mUserCenterSubCb.setVisibility(View.GONE);
            mUserCenterEditTv.setOnClickListener(v -> startActivity(UserInfoActivity.createIntent(this)));
        } else {
            mUserCenterEditTv.setVisibility(View.GONE);
            mUserCenterSubCb.setVisibility(View.VISIBLE);
            mUserCenterSubCb.setOnCheckedChangeListener((compoundButton, checked) -> mPresenter.attentionUser(mOwnerId, mUserId));
        }
//        mUserCenterAttentionTv.setText();
        mUserCenterSummaryTv.setText(user.getRemark());
        mUserCenterCollapsingTl.setTitle(user.getNickname());
        mTriangleView.setTriangleViewClickListener((v, down) -> {
            int maxLine = mUserCenterSummaryTv.getMaxLines();
            mUserCenterSummaryTv.setMaxLines(maxLine > 1 ? 1 : 4);
        });
    }

    @OnClick({R.id.user_center_edit_tv})
    public void onClick(View view){

    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        finish();
    }
}
