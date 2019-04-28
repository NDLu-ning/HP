package com.graduation.hp.repository.contact;

public interface UserTabContact {

    interface View {
        void getCurrentUserInfoSuccess(boolean isCurrentUserLogin, String icon,
                                       String nickname, float bmi, int healthyNum);
    }

    interface Presenter {
        void getCurrentUserInfo();
    }
}
