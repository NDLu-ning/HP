package com.graduation.hp.repository.http.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class FavouriteChannel implements Parcelable {

    private String channel_name;
    private String channel_tag;

    public FavouriteChannel() {
    }

    public FavouriteChannel(String channel_name, String channel_tag) {
        this.channel_name = channel_name;
        this.channel_tag = channel_tag;
    }

    protected FavouriteChannel(Parcel in) {
        channel_name = in.readString();
        channel_tag = in.readString();
    }

    public static final Creator<FavouriteChannel> CREATOR = new Creator<FavouriteChannel>() {
        @Override
        public FavouriteChannel createFromParcel(Parcel in) {
            return new FavouriteChannel(in);
        }

        @Override
        public FavouriteChannel[] newArray(int size) {
            return new FavouriteChannel[size];
        }
    };

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getChannel_tag() {
        return channel_tag;
    }

    public void setChannel_tag(String channel_tag) {
        this.channel_tag = channel_tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(channel_name);
        dest.writeString(channel_tag);
    }
}
