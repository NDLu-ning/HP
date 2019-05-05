package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.User;

public interface SettingContact {

    interface Presenter {

        void onVersionControlClick();

        void getCurrentUserInfo();

        int getTextSize();

        void updateTextSize(int textSize);

        void logout();
    }

    interface View {
        void onGetUserInfoSuccess(User user);

        void operateLocalTextSizeSuccess(int textSize);

        void onLogoutSuccess();
    }


}
