package com.graduation.hp.ui.navigation.user.info;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.core.utils.VerifyUtils;
import com.graduation.hp.repository.http.entity.vo.UserVO;

import butterknife.BindView;

public class UserUpdateFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    public interface UserUpdateFragmentListener {
        void updateUserInfo(int type, UserVO user);
    }

    public static final int TYPE_NICKNAME = 0;
    public static final int TYPE_GENDER = 1;
    public static final int TYPE_SIGNATURE = 2;

    @BindView(R.id.user_update_signature_et)
    AppCompatEditText userUpdateSignatureEt;

    @BindView(R.id.user_update_nickname_et)
    AppCompatEditText userUpdateNicknameEt;

    @BindView(R.id.user_update_gender_rg)
    RadioGroup userUpdateGenderRg;

    @BindView(R.id.user_update_male_rb)
    RadioButton userUpdateMaleRb;

    @BindView(R.id.user_update_female_rb)
    RadioButton userUpdateFemaleRb;

    private UserVO mUser;
    private int mType;
    private String[] mTitles;

    private UserUpdateFragmentListener mListener;

    public static UserUpdateFragment newInstance(int type, UserVO user) {
        UserUpdateFragment fragment = new UserUpdateFragment();
        Bundle args = new Bundle();
        args.putInt(Key.UPDATE_TYPE, type);
        args.putParcelable(Key.USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        try {
            mListener = (UserUpdateFragmentListener) context;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(context.toString() +
                    "must implement the UserUpdateFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        if (savedInstanceState != null) {
            mUser = savedInstanceState.getParcelable(Key.USER);
            mType = savedInstanceState.getInt(Key.UPDATE_TYPE, -1);
            mTitles = savedInstanceState.getStringArray(Key.UPDATE_TITIL_ARRAY);
        } else {
            Bundle args = getArguments();
            mUser = args.getParcelable(Key.USER);
            mType = args.getInt(Key.UPDATE_TYPE, -1);
            mTitles = getResources().getStringArray(R.array.user_update_titles);
        }
        if (mUser == null || mType == -1 || mTitles == null) {
            throw new IllegalArgumentException("UserUpdateFragment must receive the information of user");
        }
        initToolbar(view, mTitles[mType], R.mipmap.ic_navigation_back_white, getString(R.string.tips_update_save), R.color.md_white);
        setUpViewVisible();
    }

    private void setUpViewVisible() {
        switch (mType) {
            case UserUpdateFragment.TYPE_NICKNAME:
                userUpdateNicknameEt.setVisibility(View.VISIBLE);
                userUpdateNicknameEt.setText(mUser.getNickname());
                userUpdateNicknameEt.setSelection(mUser.getNickname().length());
                break;
            case UserUpdateFragment.TYPE_GENDER:
                userUpdateGenderRg.setOnCheckedChangeListener(this);
                userUpdateGenderRg.setVisibility(View.VISIBLE);
                userUpdateMaleRb.setChecked(mUser.getSex() == 1);
                userUpdateFemaleRb.setChecked(mUser.getSex() == 2);
                break;
            case UserUpdateFragment.TYPE_SIGNATURE:
                userUpdateSignatureEt.setVisibility(View.VISIBLE);
                userUpdateSignatureEt.setText(mUser.getRemark());
                userUpdateSignatureEt.setSelection(!TextUtils.isEmpty(mUser.getRemark()) ? mUser.getRemark().length() : 0);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(Key.USER, mUser);
        outState.putInt(Key.UPDATE_TYPE, mType);
        outState.putStringArray(Key.UPDATE_TITIL_ARRAY, mTitles);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        if (!isAdded()) return;
        ((UserInfoActivity) getActivity()).onToolbarLeftClickListener(v);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        mUser.setSex(i == R.id.user_update_male_rb ? 1 : 2);
    }

    @Override
    public void onToolbarRightClickListener(View v) {
        if (!isAdded()) return;
        switch (mType) {
            case UserUpdateFragment.TYPE_NICKNAME:
                String nickname = userUpdateNicknameEt.getText().toString();
                if (!VerifyUtils.isLengthVerified(nickname, 2, 10)) {
                    showMessage(getString(R.string.tips_update_nickname_error));
                }
                mUser.setNickname(userUpdateNicknameEt.getText().toString());
                break;
            case UserUpdateFragment.TYPE_GENDER:
                break;
            case UserUpdateFragment.TYPE_SIGNATURE:
                String signature = userUpdateSignatureEt.getText().toString();
                if (!VerifyUtils.isLengthVerified(signature, 0, 120)) {
                    showMessage(getString(R.string.tips_update_signature_error));
                }
                mUser.setRemark(signature);
                break;
        }
        mListener.updateUserInfo(mType, mUser);
        ((UserInfoActivity) getActivity()).onToolbarLeftClickListener(null);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_update;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }
}
