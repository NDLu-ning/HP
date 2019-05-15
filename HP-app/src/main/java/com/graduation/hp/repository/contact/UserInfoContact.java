package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.vo.UserVO;

import java.io.File;

public interface UserInfoContact {

    interface View {
        void onGetUserInfoSuccess(UserVO user);

        void onLogoutSuccess();
    }

    interface Presenter {

        void getCurrentUserInfo();

        void logout();

        void updateUserInfo(int type, UserVO user);

        void uploadUserProfile(File file);
    }

}
