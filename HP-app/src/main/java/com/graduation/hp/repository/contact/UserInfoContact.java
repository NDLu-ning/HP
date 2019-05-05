package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.User;

import java.io.File;

public interface UserInfoContact {

    interface View {
        void onGetUserInfoSuccess(User user);
    }

    interface Presenter {

        void getCurrentUserInfo();

        void logout();

        void updateUserInfo(int type, User user);

        void uploadUserProfile(File file);
    }

}
