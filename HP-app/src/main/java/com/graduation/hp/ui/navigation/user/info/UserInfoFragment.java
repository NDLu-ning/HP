package com.graduation.hp.ui.navigation.user.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;

import butterknife.BindView;

public class UserInfoFragment extends BaseFragment   {

    public interface UserInfoFragmentListener {
        void getUserInfo();
    }

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @BindView(R.id.user_info_nickname_tv)
    AppCompatTextView mUserInfoNicknameTv;

    @BindView(R.id.user_info_profile_iv)
    AppCompatImageView mUserInfoProfileIv;

    @BindView(R.id.user_info_gender_tv)
    AppCompatTextView mUserInfoGenderTv;

    @BindView(R.id.user_info_signature_cl)
    AppCompatTextView mUserInfoSignatureCl;

    @Override
    protected void init(Bundle savedInstanceState, View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }
}
