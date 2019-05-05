package com.graduation.hp.ui.navigation.user.info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.graduation.hp.R;
import com.graduation.hp.app.event.UploadProfileEvent;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.core.utils.ToastUtils;
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.repository.preferences.PreferencesHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoFragment extends BaseFragment {

    private User mUser;

    public interface UserInfoFragmentListener {
        void getCurrentUserInfo();

        void logout();

        void skipToUserUpdateView(int type, User user);

        void uploadProfileContainerClick();
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
        initToolbar(view, getString(R.string.tips_user_info), R.mipmap.ic_navigation_back_white);
        view.findViewById(R.id.user_info_logout_btn).setOnClickListener(v -> mListener.logout());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mUser != null) {
            onGetUserInfoSuccess(mUser);
        }
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
    public void onUpdateContainerClick(View v) {
        if (mUser == null) return;
        switch (v.getId()) {
            case R.id.user_info_profile_cl:
                mListener.uploadProfileContainerClick();
                break;
            case R.id.user_info_gender_cl:
                mListener.skipToUserUpdateView(UserInfoActivity.FRAGMENT_IS_UPDATE_GENDER, mUser);
                break;
            case R.id.user_info_signature_cl:
                mListener.skipToUserUpdateView(UserInfoActivity.FRAGMENT_IS_UPDATE_SIGNATURE, mUser);
                break;
            case R.id.user_info_nickname_cl:
                mListener.skipToUserUpdateView(UserInfoActivity.FRAGMENT_IS_UPDATE_NICKNAME, mUser);
                break;
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetUserInfoSuccess(User user) {
        if (!isAdded()) return;
        this.mUser = user;
        mUserInfoNicknameTv.setText(user.getNickname());
        mUserInfoGenderTv.setText(user.getSex() == 1 ?
                getString(R.string.tips_user_male) : getString(R.string.tips_user_female));
        GlideUtils.loadUserHead(mUserInfoProfileIv, user.getHeadUrl());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadProfileSuccess(UploadProfileEvent event) {
        ToastUtils.show(getContext(), getString(R.string.tips_upload_success));
        GlideUtils.loadUserHead(mUserInfoProfileIv, event.getUrl());
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        if (!isAdded()) return;
        ((UserInfoActivity) getActivity()).onToolbarLeftClickListener(v);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }
}
