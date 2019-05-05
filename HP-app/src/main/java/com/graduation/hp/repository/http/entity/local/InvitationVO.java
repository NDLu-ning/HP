package com.graduation.hp.repository.http.entity.local;

import com.graduation.hp.repository.http.entity.InvitationPO;

public class InvitationVO extends InvitationPO {
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像url
     */
    private String headUrl;
    /**
     * 体质字符
     */
    private String physiqueStr;

    public InvitationVO() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getPhysiqueStr() {
        return physiqueStr;
    }

    public void setPhysiqueStr(String physiqueStr) {
        this.physiqueStr = physiqueStr;
    }
}
