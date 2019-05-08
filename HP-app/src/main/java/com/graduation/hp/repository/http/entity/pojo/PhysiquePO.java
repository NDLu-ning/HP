package com.graduation.hp.repository.http.entity.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * t_physique表对应实体
 * 
 * @author shengting_wang
 */
public class PhysiquePO implements Parcelable {

    /**
     * 体质
     */
    private Long id;

    /**
     * 类型
     */
    private String typed;

    /**
     * 该类型的人数
     */
    private Long num;

    /**
     * 特点
     */
    private String detail;

    /**
     * 调养方案
     */
    private String recuperate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public PhysiquePO() {
    }

    protected PhysiquePO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        typed = in.readString();
        if (in.readByte() == 0) {
            num = null;
        } else {
            num = in.readLong();
        }
        detail = in.readString();
        recuperate = in.readString();
    }

    public static final Creator<PhysiquePO> CREATOR = new Creator<PhysiquePO>() {
        @Override
        public PhysiquePO createFromParcel(Parcel in) {
            return new PhysiquePO(in);
        }

        @Override
        public PhysiquePO[] newArray(int size) {
            return new PhysiquePO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTyped() {
        return typed;
    }

    public void setTyped(String typed) {
        this.typed = typed;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRecuperate() {
        return recuperate;
    }

    public void setRecuperate(String recuperate) {
        this.recuperate = recuperate;
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
        parcel.writeString(typed);
        if (num == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(num);
        }
        parcel.writeString(detail);
        parcel.writeString(recuperate);
    }
}