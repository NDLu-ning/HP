package com.graduation.hp.repository.http.entity.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * t_answer表对应实体
 * 
 * @author shengting_wang
 */
public class AnswerPO implements Parcelable {

    /**
     * 答案主键
     */
    private Long id;

    /**
     * 答案内容
     */
    private String answerContext;

    /**
     * 答案分数
     */
    private Long score;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    public AnswerPO() {
    }

    public AnswerPO(String answerContext) {
        this.answerContext = answerContext;
    }

    public AnswerPO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        answerContext = in.readString();
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readLong();
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

    public static final Creator<AnswerPO> CREATOR = new Creator<AnswerPO>() {
        @Override
        public AnswerPO createFromParcel(Parcel in) {
            return new AnswerPO(in);
        }

        @Override
        public AnswerPO[] newArray(int size) {
            return new AnswerPO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerContext() {
        return answerContext;
    }

    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
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
        parcel.writeString(answerContext);
        if (score == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(score);
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