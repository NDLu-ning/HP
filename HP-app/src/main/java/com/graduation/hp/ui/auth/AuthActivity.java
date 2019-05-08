package com.graduation.hp.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.app.event.AuthEvent;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.presenter.AuthPresenter;
import com.graduation.hp.repository.contact.AuthContact;
import com.graduation.hp.ui.auth.login.LoginFragment;
import com.graduation.hp.ui.auth.register.RegisterFragment;
import com.graduation.hp.ui.auth.reset.InputPhoneFragment;
import com.graduation.hp.ui.auth.reset.UpdatePasswordFragment;
import com.graduation.hp.ui.navigation.NavigationTabActivity;

import org.greenrobot.eventbus.EventBus;

public class AuthActivity extends SingleFragmentActivity<AuthPresenter>
        implements AuthContact.View, LoginFragment.LoginFragmentListener,
        RegisterFragment.RegisterFragmentListener,
        InputPhoneFragment.InputPhoneFragmentListener,
        UpdatePasswordFragment.UpdatePasswordFragmentListener {

    private static final String KEY_FIRST_FRAGMENT = "first_fragment";

    public static final int FRAGMENT_IS_SIGN_IN = 1;
    public static final int FRAGMENT_IS_SIGN_UP = 2;
    public static final int FRAGMENT_IS_INPUT_PHONE = 3;
    private static final int FRAGMENT_IS_UPDATE_PASSWORD = 4;

    public static final int SEND_SUCCESS = 8001;

    private int mCurFragment;
    private String mSourceCode;

    public static Intent createIntent(Context context) {
        return new Intent(context, AuthActivity.class);
    }

    public static Intent createIntent(Context context, int firstFragment) {
        final Intent intent = new Intent(context, AuthActivity.class);
        intent.putExtra(KEY_FIRST_FRAGMENT, firstFragment);
        return intent;
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_auth;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (savedInstanceState != null) {
            mCurFragment = savedInstanceState.getInt(KEY_FIRST_FRAGMENT);
        } else {
            mCurFragment = getIntent().getIntExtra(KEY_FIRST_FRAGMENT, FRAGMENT_IS_SIGN_IN);
        }
        getSupportFragmentManager().addOnBackStackChangedListener(this::setHomeButtonEnabled);
        initToolbar(mRootView, "", R.mipmap.ic_close_24, 0, R.color.md_white);
    }

    private void setHomeButtonEnabled() {
        if (mCurFragment != FRAGMENT_IS_SIGN_IN || getSupportFragmentManager().getBackStackEntryCount() > 0) {
            setToolbarLeftDrawableRes(R.mipmap.ic_navigation_back);
        } else {
            setToolbarLeftDrawableRes(R.mipmap.ic_close_24);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_FIRST_FRAGMENT, mCurFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected Fragment createMainContentFragment() {
        switch (mCurFragment) {
            case FRAGMENT_IS_INPUT_PHONE:
                return InputPhoneFragment.newInstance(null);
            case FRAGMENT_IS_SIGN_UP:
                return RegisterFragment.newInstance();
            case FRAGMENT_IS_SIGN_IN:
            default:
                return LoginFragment.newInstance();
        }
    }

    @Override
    public void onTryToLogin(String username, String password) {
        mPresenter.onTryToLogin(username, password);
    }

    @Override
    public void goToResetPassword() {
        replaceMainContentFragment(InputPhoneFragment.newInstance(null), true);
    }

    @Override
    public void goToRegister() {
        replaceMainContentFragment(RegisterFragment.newInstance(), true);
    }

    @Override
    public void verifyCode(String phoneNumber, String code) {
        mPresenter.verifyCode(phoneNumber, code, mSourceCode);
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
    public void onToolbarLeftClickListener(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void onRegisterInputError(AuthEvent authEvent) {
//        ((RegisterFragment) getMainContentFragment()).onRegisterError(authEvent);
        EventBus.getDefault().post(authEvent);
    }

    @Override
    public void onVerifyPhoneNumberResult(String phoneNumber) {
        replaceMainContentFragment(UpdatePasswordFragment.newInstance(phoneNumber));
    }

    @Override
    public void onLoginSuccess() {
        startActivity(NavigationTabActivity.createIntent(this, true));
    }

    @Override
    public void onRegisterSuccess() {
        replaceMainContentFragment(LoginFragment.newInstance());
    }

    @Override
    public void onSendCodeSuccess(String code) {
        EventBus.getDefault().post(new AuthEvent(FRAGMENT_IS_INPUT_PHONE, SEND_SUCCESS, ""));
    }

    @Override
    public void onTryToSendCode(String phone) {
        mPresenter.onTryToSendCode(phone);
    }

    @Override
    public void onTryToSignup(String phone, String password, String repassword, String nickname) {
        mPresenter.onTryToSignup(phone, password, repassword, nickname);
    }

    @Override
    public void updatePassword(String phoneNumber, String password, String repassword) {
        mPresenter.updatePassword(phoneNumber, password, repassword);
    }
}
