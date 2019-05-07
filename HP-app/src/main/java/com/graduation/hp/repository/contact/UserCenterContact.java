package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.wrapper.UserVOWrapper;

public interface UserCenterContact {

    interface View {
        void onGetUserSuccess(UserVOWrapper user);
    }

    interface Presenter {
        void onGetUserInfo(long id);

        void attentionUser(long authorId);
    }

}
