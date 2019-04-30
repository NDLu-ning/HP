package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.User;

public interface UserCenterContact {

    interface View {
        void onGetUserSuccess(User user);
    }

    interface Presenter {
        void onGetUserInfo(long id);

        void attentionUser(long ownerId, long userId);
    }

}
