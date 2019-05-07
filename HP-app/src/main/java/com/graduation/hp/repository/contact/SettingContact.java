package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.vo.UserVO;

public interface SettingContact {

    interface Presenter {

        void onVersionControlClick();

        void getCurrentUserInfo();

        int getTextSize();

        void updateTextSize(int textSize);

        void logout();
    }

    interface View {
        void onGetUserInfoSuccess(UserVO user);

        void operateLocalTextSizeSuccess(int textSize);

        void onLogoutSuccess();
    }


}
