package com.graduation.hp.repository.http.entity.wrapper;

import com.graduation.hp.repository.http.entity.vo.UserVO;

public class UserVOWrapper {

    private UserVO user;
    private long attentionCount;

    public UserVOWrapper() {
    }

    public UserVOWrapper(UserVO user, long attentionCount) {
        this.user = user;
        this.attentionCount = attentionCount;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public long getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(long attentionCount) {
        this.attentionCount = attentionCount;
    }
}
