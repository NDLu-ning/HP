package com.graduation.hp.repository.http.entity.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.graduation.hp.core.repository.http.bean.Page;

import java.util.Date;

/**
 * t_user表对应实体
 *
 * @author shengting_wang
 */
public class UserVO extends Page implements Parcelable {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名(手机号码)
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 体质id
     */
    private Long physiquId;

    /**
     * 性别(1:男，0:女)
     */
    private Integer sex;

    /**
     * 头像url
     */
    private String headUrl;

    /**
     * token令牌
     */
    private String token;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public UserVO() {
    }

    protected UserVO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        username = in.readString();
        password = in.readString();
        nickname = in.readString();
        if (in.readByte() == 0) {
            physiquId = null;
        } else {
            physiquId = in.readLong();
        }
        if (in.readByte() == 0) {
            sex = null;
        } else {
            sex = in.readInt();
        }
        headUrl = in.readString();
        token = in.readString();
        remark = in.readString();
    }

    public static final Creator<UserVO> CREATOR = new Creator<UserVO>() {
        @Override
        public UserVO createFromParcel(Parcel in) {
            return new UserVO(in);
        }

        @Override
        public UserVO[] newArray(int size) {
            return new UserVO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getPhysiquId() {
        return physiquId;
    }

    public void setPhysiquId(Long physiquId) {
        this.physiquId = physiquId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(nickname);
        if (physiquId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(physiquId);
        }
        if (sex == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(sex);
        }
        parcel.writeString(headUrl);
        parcel.writeString(token);
        parcel.writeString(remark);
    }
}