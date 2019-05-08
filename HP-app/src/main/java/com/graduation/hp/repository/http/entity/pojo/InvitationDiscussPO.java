package com.graduation.hp.repository.http.entity.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * t_invitation_discuss表对应实体
 * 
 * @author shengting_wang
 */
public class InvitationDiscussPO implements Parcelable {

    /**
     * 帖子评论主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 类型（1：评论 2：:对话）
     */
    private Integer discussType;

    /**
     * 
     */
    private Long talkerUserId;

    /**
     * 帖子id
     */
    private Long invitationId;

    /**
     * 内容
     */
    private String context;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 评论时间
     */
    private Date updateTime;
    
    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 交谈者用户昵称
     */
    private String talkNickname;

    public InvitationDiscussPO() {
    }

    protected InvitationDiscussPO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readLong();
        }
        if (in.readByte() == 0) {
            discussType = null;
        } else {
            discussType = in.readInt();
        }
        if (in.readByte() == 0) {
            talkerUserId = null;
        } else {
            talkerUserId = in.readLong();
        }
        if (in.readByte() == 0) {
            invitationId = null;
        } else {
            invitationId = in.readLong();
        }
        context = in.readString();
        nickname = in.readString();
        talkNickname = in.readString();
        if (in.readByte() == 0) {
            createTime = null;
        } else {
            long startTime = in.readLong();
            createTime = new Date(startTime);
        }
        if (in.readByte() == 0) {
            updateTime = null;
        } else {
            long endTime = in.readLong();
            updateTime = new Date(endTime);
        }
    }

    public static final Creator<InvitationDiscussPO> CREATOR = new Creator<InvitationDiscussPO>() {
        @Override
        public InvitationDiscussPO createFromParcel(Parcel in) {
            return new InvitationDiscussPO(in);
        }

        @Override
        public InvitationDiscussPO[] newArray(int size) {
            return new InvitationDiscussPO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getDiscussType() {
        return discussType;
    }

    public void setDiscussType(Integer discussType) {
        this.discussType = discussType;
    }

    public Long getTalkerUserId() {
        return talkerUserId;
    }

    public void setTalkerUserId(Long talkerUserId) {
        this.talkerUserId = talkerUserId;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTalkNickname() {
        return talkNickname;
    }

    public void setTalkNickname(String talkNickname) {
        this.talkNickname = talkNickname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        if (userId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(userId);
        }
        if (discussType == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(discussType);
        }
        if (talkerUserId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(talkerUserId);
        }
        if (invitationId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(invitationId);
        }
        parcel.writeString(context);
        parcel.writeString(nickname);
        parcel.writeString(talkNickname);
        if (createTime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(createTime.getTime());
        }
        if (updateTime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(updateTime.getTime());
        }
    }
}