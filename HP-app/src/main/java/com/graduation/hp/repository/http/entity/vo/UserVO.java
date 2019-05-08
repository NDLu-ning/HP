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
    private long id;

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
    private long physiquId;

    /**
     * 性别(1:男，0:女)
     */
    private int sex;

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
        super(in);
        id = in.readLong();
        username = in.readString();
        password = in.readString();
        nickname = in.readString();
        physiquId = in.readLong();
        sex = in.readInt();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getPhysiquId() {
        return physiquId;
    }

    public void setPhysiquId(long physiquId) {
        this.physiquId = physiquId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
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
        super.writeToParcel(parcel, i);
        parcel.writeLong(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(nickname);
        parcel.writeLong(physiquId);
        parcel.writeInt(sex);
        parcel.writeString(headUrl);
        parcel.writeString(token);
        parcel.writeString(remark);
    }
}