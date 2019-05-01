package com.graduation.hp.repository.http.entity.local;

import com.graduation.hp.repository.http.entity.User;

public class UserVo {

    private User user;
    private long attentionCount;

    public UserVo() {
    }

    public UserVo(User user, long attentionCount) {
        this.user = user;
        this.attentionCount = attentionCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(long attentionCount) {
        this.attentionCount = attentionCount;
    }
}
