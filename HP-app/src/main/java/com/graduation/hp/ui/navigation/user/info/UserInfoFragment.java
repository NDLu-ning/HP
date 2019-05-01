package com.graduation.hp.ui.navigation.user.info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoFragment extends BaseFragment {

    public interface UserInfoFragmentListener {
        void getCurrentUserInfo();

        void logout();
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

    private UserInfoFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        try {
            mListener = (UserInfoFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement UserInfoFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        view.findViewById(R.id.user_info_logout_btn).setOnClickListener(v -> mListener.logout());
    }

    @Override
    protected void onLazyLoad() {
        mListener.getCurrentUserInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
    }

    @OnClick({R.id.user_info_gender_cl, R.id.user_info_profile_cl
            , R.id.user_info_nickname_cl, R.id.user_info_signature_cl})
    public void onUpdateContainerClick(View v){
        switch (v.getId()){

        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }
}
