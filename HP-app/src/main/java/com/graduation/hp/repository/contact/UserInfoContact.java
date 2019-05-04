package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.User;
import com.graduation.hp.ui.navigation.user.info.UserInfoActivity;
import com.graduation.hp.ui.navigation.user.info.UserInfoFragment;

public interface UserInfoContact {

    interface View {
        void onGetUserInfoSuccess(User user);
    }

    interface Presenter extends UserInfoFragment.UserInfoFragmentListener {

    }

}
