package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.wrapper.UserVOWrapper;

public interface UserCenterContact {

    interface View {
        void onGetUserSuccess(UserVOWrapper user);

        void operateAttentionStateSuccess(boolean isFocusOn);

        void onGetAttentionSuccess(boolean isFocusOn);
    }

    interface Presenter {
        void onGetUserInfo(long id);

        void attentionUser(long authorId);

        void isFocusOn(long authorId);
    }

}
