package com.graduation.hp.ui.navigation.user.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.RadioGroup;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.event.UserUpdateEvent;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.repository.http.entity.User;

import butterknife.BindView;

public class UserUpdateFragment extends BaseFragment {

    public static final int TYPE_NICKNAME = 0;
    public static final int TYPE_GENDER = 1;
    public static final int TYPE_SIGNATURE = 2;

    public interface UserUpdateFragmentListener {
        void updateUserInfo(int type, User user);
    }

    @BindView(R.id.user_info_signature_cl)
    AppCompatEditText userInfoSignatureCl;

    @BindView(R.id.user_info_nickname_tv)
    AppCompatEditText userInfoNicknameTv;

    @BindView(R.id.user_info_gender_tv)
    RadioGroup userInfoGenderTv;

    private User mUser;
    private String[] mTitles;
    private int mType;

    public static UserUpdateFragment newInstance(int type, User user) {
        UserUpdateFragment fragment = new UserUpdateFragment();
        Bundle args = new Bundle();
        args.putInt(Key.UPDATE_TYPE, type);
        args.putParcelable(Key.USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        if (savedInstanceState != null) {
            mUser = savedInstanceState.getParcelable(Key.USER);
            mType = savedInstanceState.getInt(Key.UPDATE_TYPE);
            mTitles = savedInstanceState.getStringArray(Key.UPDATE_TITIL_ARRAY);
        } else {
            Bundle args = getArguments();
            mUser = args.getParcelable(Key.USER);
            mType = args.getInt(Key.UPDATE_TYPE);
            mTitles = getResources().getStringArray(R.array.user_update_titles);
        }
        initToolbar(view, mTitles[mType], R.mipmap.ic_navigation_back_white, getString(R.string.tips_update_save), R.color.md_white);
        setUpViewVisible();
    }

    private void setUpViewVisible() {
        switch (mType) {
            case UserUpdateFragment.TYPE_NICKNAME:
                userInfoNicknameTv.setVisibility(View.VISIBLE);
                break;
            case UserUpdateFragment.TYPE_GENDER:
                userInfoGenderTv.setVisibility(View.VISIBLE);
                break;
            case UserUpdateFragment.TYPE_SIGNATURE:
                userInfoSignatureCl.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Key.USER, mUser);
        outState.putInt(Key.UPDATE_TYPE, mType);
        outState.putStringArray(Key.UPDATE_TITIL_ARRAY, mTitles);
    }

    @Override
    public void onToolbarLeftClickListener(View v) {

    }

    @Override
    public void onToolbarRightClickListener(View v) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_update;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }
}
