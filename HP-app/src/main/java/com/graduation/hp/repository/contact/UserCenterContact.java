package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.local.UserVO;

public interface UserCenterContact {

    interface View {
        void onGetUserSuccess(UserVO user);
    }

    interface Presenter {
        void onGetUserInfo(long id);

        void attentionUser(long authorId);
    }

}
