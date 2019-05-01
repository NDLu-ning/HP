package com.graduation.hp.repository.http.entity.local;

import android.os.Parcel;
import android.os.Parcelable;

public class ChannelVo implements Parcelable {

    private long id;
    private String titile;

    public ChannelVo() {
    }

    public ChannelVo(long id, String titile) {
        this.id = id;
        this.titile = titile;
    }

    protected ChannelVo(Parcel in) {
        id = in.readLong();
        titile = in.readString();
    }

    public static final Creator<ChannelVo> CREATOR = new Creator<ChannelVo>() {
        @Override
        public ChannelVo createFromParcel(Parcel in) {
            return new ChannelVo(in);
        }

        @Override
        public ChannelVo[] newArray(int size) {
            return new ChannelVo[size];
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
