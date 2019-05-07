package com.graduation.hp.repository.http.entity.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * t_question表对应实体
 *
 * @author shengting_wang
 * @date 2019-04-30
 */
public class QuestionPO implements Parcelable {
    /**
     * 问题的主键
     */
    private Long id;

    /**
     * 内容
     */
    private String context;

    /**
     * 体质主键
     */
    private Long physiqueId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public QuestionPO() {
    }

    protected QuestionPO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        context = in.readString();
        if (in.readByte() == 0) {
            physiqueId = null;
        } else {
            physiqueId = in.readLong();
        }
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

    public static final Creator<QuestionPO> CREATOR = new Creator<QuestionPO>() {
        @Override
        public QuestionPO createFromParcel(Parcel in) {
            return new QuestionPO(in);
        }

        @Override
        public QuestionPO[] newArray(int size) {
            return new QuestionPO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Long getPhysiqueId() {
        return physiqueId;
    }

    public void setPhysiqueId(Long physiqueId) {
        this.physiqueId = physiqueId;
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
        parcel.writeString(context);
        if (physiqueId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(physiqueId);
        }
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