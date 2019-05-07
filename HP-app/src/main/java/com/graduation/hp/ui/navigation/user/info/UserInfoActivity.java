package com.graduation.hp.ui.navigation.user.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.repository.entity.BottomSheetOption;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.core.ui.bottomsheet.ListSelectionBottomSheetFragment;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.core.utils.UploadAvatarHelper;
import com.graduation.hp.presenter.UserInfoPresenter;
import com.graduation.hp.repository.contact.UserInfoContact;
import com.graduation.hp.repository.http.entity.vo.UserVO;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class UserInfoActivity extends SingleFragmentActivity<UserInfoPresenter>
        implements UserInfoContact.View,
        UserInfoFragment.UserInfoFragmentListener,
        UserUpdateFragment.UserUpdateFragmentListener,
        ListSelectionBottomSheetFragment.ListSelectionBottomSheetFragmentListener,
        UploadAvatarHelper.UploadAvatarHelperListener {

    private static final String KEY_FRAGMENT = "fragment";

    public static final int FRAGMENT_IS_USER_INFO = 4;
    public static final int FRAGMENT_IS_UPDATE_NICKNAME = 0;
    public static final int FRAGMENT_IS_UPDATE_GENDER = 1;
    public static final int FRAGMENT_IS_UPDATE_SIGNATURE = 2;
    private int mCurFragment;
    private UploadAvatarHelper mUploadAvatarHelper;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        intent.putExtra(KEY_FRAGMENT, FRAGMENT_IS_USER_INFO);
        return intent;
    }

    @Override
    protected Fragment createMainContentFragment() {
        return UserInfoFragment.newInstance();
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
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mUploadAvatarHelper = new UploadAvatarHelper(this, this);
        if (savedInstanceState != null) {
            mCurFragment = savedInstanceState.getInt(KEY_FRAGMENT, FRAGMENT_IS_USER_INFO);
            mUploadAvatarHelper.onRestoreInstanceState(savedInstanceState);
        } else {
            Intent intent = getIntent();
            mCurFragment = intent.getIntExtra(KEY_FRAGMENT, FRAGMENT_IS_USER_INFO);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mUploadAvatarHelper.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void getCurrentUserInfo() {
        mPresenter.getCurrentUserInfo();
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_user_info;
    }

    @Override
    public void logout() {
        mPresenter.logout();
    }

    @Override
    public void skipToUserUpdateView(int type, UserVO user) {
        replaceMainContentFragment(UserUpdateFragment.newInstance(type, user), true);
    }

    @Override
    public void uploadProfileContainerClick() {
        mUploadAvatarHelper.changeAvatar();
    }

    @Override
    public void onGetUserInfoSuccess(UserVO user) {
        EventBus.getDefault().post(user);
    }

    @Override
    public void updateUserInfo(int type, UserVO user) {
        LogUtils.d(type + "," + user.getNickname() + "," + user.getSex() + "," + user.getRemark());
//        if (mCurFragment != FRAGMENT_IS_USER_INFO) {
//            mPresenter.updateUserInfo(type, user);
//        }
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void onAvatarUpload(String largeFilePath, String smallFilePath) {
        File file = new File(largeFilePath);
        if (!file.exists()) {
            showMessage(getString(R.string.tips_happen_unknown_error));
            return;
        }
        mPresenter.uploadUserProfile(file);
    }

    @Override
    public void onBottomSheetOptionSelected(BottomSheetOption option) {
        mUploadAvatarHelper.onBottomSheetOptionSelected(option);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mUploadAvatarHelper.onActivityResult(requestCode, resultCode, data);
    }
}
