package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.User;

public interface UserTabContact {

    interface View {
        void getCurrentUserInfoSuccess(User user);
    }

    interface Presenter {
        void getCurrentUserInfo();
    }
}
