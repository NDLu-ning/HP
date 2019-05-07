package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.vo.UserVO;

public interface UserTabContact {

    interface View {
        void getCurrentUserInfoSuccess(UserVO user);
    }

    interface Presenter {
        void getCurrentUserInfo();
    }
}
