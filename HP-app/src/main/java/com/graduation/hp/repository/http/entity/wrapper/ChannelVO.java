package com.graduation.hp.repository.http.entity.wrapper;

import android.os.Parcel;
import android.os.Parcelable;

public class ChannelVO implements Parcelable {

    private long id;
    private String type;
    private String title;

    public ChannelVO() {
    }

    public ChannelVO(long id, String title) {
        this.id = id;
        this.title = title;
    }

    protected ChannelVO(Parcel in) {
        id = in.readLong();
        title = in.readString();
    }

    public static final Creator<ChannelVO> CREATOR = new Creator<ChannelVO>() {
        @Override
        public ChannelVO createFromParcel(Parcel in) {
            return new ChannelVO(in);
        }

        @Override
        public ChannelVO[] newArray(int size) {
            return new ChannelVO[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
    }
}
