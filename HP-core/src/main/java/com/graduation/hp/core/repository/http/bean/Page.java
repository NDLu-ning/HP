package com.graduation.hp.core.repository.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Locale;

/**
 * 分页组件
 *
 * @author wst
 * @date 2018/12/26 21:11
 **/

public class Page implements Parcelable {

    /**
     * 一页有几个
     */
    private Integer limit;

    /**
     * 第几个开始 （10,20，30）
     */
    private Integer offset;

    /**
     * 开始时间
     */
    private Date createTimeStart;

    /**
     * 结束时间
     */
    private Date createTimeEnd;

    public Page() {
        offset = 0;
        limit = 10;
    }


    protected Page(Parcel in) {
        if (in.readByte() == 0) {
            limit = null;
        } else {
            limit = in.readInt();
        }
        if (in.readByte() == 0) {
            offset = null;
        } else {
            offset = in.readInt();
        }
        if (in.readByte() == 0) {
            createTimeStart = null;
        } else {
            long startTime = in.readLong();
            createTimeStart = new Date(startTime);
        }
        if (in.readByte() == 0) {
            createTimeEnd = null;
        } else {
            long endTime = in.readLong();
            createTimeEnd = new Date(endTime);
        }
    }

    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (limit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(limit);
        }
        if (offset == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(offset);
        }
        if (createTimeStart == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(createTimeStart.getTime());
        }
        if (createTimeEnd == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(createTimeEnd.getTime());
        }
    }
}
