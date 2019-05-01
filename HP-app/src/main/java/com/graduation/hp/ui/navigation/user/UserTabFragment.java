package com.graduation.hp.ui.navigation.user;

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
import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.ui.navigation.NavigationTabActivity;
import com.graduation.hp.ui.navigation.user.center.UserCenterActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R.id.my_center_cl)
    ConstraintLayout myCenterCl;

    private User mUser;


    public static UserTabFragment newInstance() {
        UserTabFragment fragment = new UserTabFragment();
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        if (savedInstanceState != null) {
            mUser = savedInstanceState.getParcelable(Key.USER);
            setViewByLoginState();
        } else {
            mPresenter.getCurrentUserInfo();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(Key.USER, mUser);
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

    @OnClick({R.id.my_setting_cl, R.id.my_message_cl, R.id.my_info_cl, R.id.my_test_cl})
    public void onFunctionLayoutClick(View view) {
        int id = view.getId();
        if (mUser == null &&
                (id == R.id.my_info_cl || id == R.id.my_message_cl)) {
            ((NavigationTabActivity) getActivity()).skipToLoginPage();
            showMessage(getString(R.string.tips_please_login_first));
        }else {
            switch (id) {
                case R.id.my_setting_cl:
                    break;
                case R.id.my_message_cl:
                    break;
                case R.id.my_info_cl:
                    break;
                case R.id.my_test_cl:
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void getCurrentUserInfoSuccess(User user) {
        this.mUser = user;
        setViewByLoginState();
    }

    private void setViewByLoginState() {
        if (isAdded()) {
            if (mUser == null) {
                myNameTv.setText(getString(R.string.tips_myself_login));
                myCenterCl.setOnClickListener(v -> ((NavigationTabActivity) getActivity()).skipToLoginPage());
            } else {
                int width = ScreenUtils.dip2px(getContext(), 80);
                GlideUtils.loadUserHead(myPhotoIv, mUser.getHeadUrl(), width, width);
                myNameTv.setText(mUser.getNickname());
                myHealthyTitleTv.setVisibility(View.VISIBLE);
                myHealthyTagTv.setText(mUser.getPhysiquId() + "");
                myCenterCl.setOnClickListener(v -> skipToUserDetailPage());
            }
        }
    }

    private void skipToUserDetailPage() {
        startActivity(UserCenterActivity.createIntent(getContext(), mUser.getId()));
    }
}
