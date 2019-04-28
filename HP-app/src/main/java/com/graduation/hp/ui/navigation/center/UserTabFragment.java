package com.graduation.hp.ui.navigation.center;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.core.utils.ScreenUtils;
import com.graduation.hp.presenter.UserTabPresenter;
import com.graduation.hp.repository.contact.UserTabContact;
import com.graduation.hp.ui.navigation.NavigationTabActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class UserTabFragment extends BaseFragment<UserTabPresenter>
        implements UserTabContact.View {

    @BindView(R.id.my_photo_iv)
    ImageView myPhotoIv;

    @BindView(R.id.my_name_tv)
    TextView myNameTv;

    @BindView(R.id.my_healthy_title_tv)
    TextView myHealthyTitleTv;

    @BindView(R.id.my_healthy_tag_tv)
    TextView myHealthyTagTv;

    @BindView(R.id.my_info_cl)
    ConstraintLayout myInfoCl;

    private boolean isCurUserLogin;

    private String mUserNickname;
    private String mUserIcon;
    private int mUserHealthyNum;
    private double mUserBMI;


    public static UserTabFragment newInstance() {
        UserTabFragment fragment = new UserTabFragment();
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        if (savedInstanceState != null) {
            isCurUserLogin = savedInstanceState.getBoolean(Key.IS_CURRENT_USER_LOGIN, false);
            mUserIcon = savedInstanceState.getString(Key.USER_ICON);
            mUserBMI = savedInstanceState.getDouble(Key.USER_BMI, 0D);
            mUserNickname = savedInstanceState.getString(Key.USER_NICKNAME);
            mUserHealthyNum = savedInstanceState.getInt(Key.USER_HEALTHY_NUM, 0);
        } else {
            mPresenter.getCurrentUserInfo();
        }
        setViewByLoginState();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(Key.IS_CURRENT_USER_LOGIN, isCurUserLogin);
        outState.putString(Key.USER_ICON, mUserIcon);
        outState.putDouble(Key.USER_BMI, mUserBMI);
        outState.putString(Key.USER_NICKNAME, mUserNickname);
        outState.putInt(Key.USER_HEALTHY_NUM, mUserHealthyNum);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myself;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.my_setting_cl, R.id.my_message_cl, R.id.my_post_cl, R.id.my_test_cl})
    public void onFunctionLayoutClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.my_setting_cl:
                break;
            case R.id.my_message_cl:
                break;
            case R.id.my_post_cl:
                break;
            case R.id.my_test_cl:
                break;
        }
    }

    @Override
    public void getCurrentUserInfoSuccess(boolean isCurrentUserLogin, String icon, String nickname, float bmi, int healthyNum) {
        isCurUserLogin = isCurrentUserLogin;
        mUserIcon = icon;
        mUserNickname = nickname;
        mUserHealthyNum = healthyNum;
        mUserBMI = bmi;
    }

    private void setViewByLoginState() {
        int width = ScreenUtils.dip2px(getContext(), 80);
        GlideUtils.loadUserHead(myPhotoIv, mUserIcon, width, width);
        if (!isCurUserLogin) {
            myNameTv.setText(getString(R.string.tips_myself_login));
            myInfoCl.setOnClickListener(v -> ((NavigationTabActivity)getActivity()).skipToLoginPage());
        } else {
            myNameTv.setText(mUserNickname);
            myHealthyTitleTv.setVisibility(View.VISIBLE);
            myHealthyTagTv.setText(mUserHealthyNum + "");
            myInfoCl.setOnClickListener(v -> skipToUserDetailPage());
        }
    }

    private void skipToUserDetailPage() {

    }
}
