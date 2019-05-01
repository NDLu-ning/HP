package com.graduation.hp.ui.navigation.user.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.RadioGroup;

import com.graduation.hp.R;
import com.graduation.hp.app.event.UserUpdateEvent;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.repository.http.entity.User;

import butterknife.BindView;

public class UserUpdateFragment extends BaseFragment {

    @BindView(R.id.user_info_signature_cl)
    AppCompatEditText userInfoSigatureCl;

    @BindView(R.id.user_info_nickname_tv)
    AppCompatEditText userInfoNicknameTv;

    @BindView(R.id.user_info_gender_tv)
    RadioGroup userInfoGenderTv;

    private User user;
    private String title;
    private int type;

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        if (savedInstanceState != null) {

        } else {

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_update;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }
}
