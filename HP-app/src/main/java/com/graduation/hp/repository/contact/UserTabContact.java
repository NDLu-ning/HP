package com.graduation.hp.repository.contact;

public interface UserTabContact {

    interface View {
        void getCurrentUserInfoSuccess(boolean isCurrentUserLogin, String icon,
                                       String nickname, float bmi, long healthyNum);
    }

    interface Presenter {
        void getCurrentUserInfo();
    }
}
