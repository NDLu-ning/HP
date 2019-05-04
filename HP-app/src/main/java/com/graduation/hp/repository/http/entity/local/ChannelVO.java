package com.graduation.hp.repository.http.entity.local;

import android.os.Parcel;
import android.os.Parcelable;

public class ChannelVO implements Parcelable {

    private long id;
    private String titile;

    public ChannelVO() {
    }

    public ChannelVO(long id, String titile) {
        this.id = id;
        this.titile = titile;
    }

    protected ChannelVO(Parcel in) {
        id = in.readLong();
        titile = in.readString();
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

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(titile);
    }
}
