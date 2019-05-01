package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.local.UserVo;

public interface UserCenterContact {

    interface View {
        void onGetUserSuccess(UserVo user);
    }

    interface Presenter {
        void onGetUserInfo(long id);

        void attentionUser(long authorId);
    }

}
