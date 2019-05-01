package com.graduation.hp.app.event;

import android.os.Parcel;
import android.os.Parcelable;

public class UserUpdateEvent implements Parcelable {

    public int viewId;
    public String value;
    public String title;

    public UserUpdateEvent(){
    }

    public UserUpdateEvent(int viewId, String value, String title) {
        this.viewId = viewId;
        this.value = value;
        this.title = title;
    }

    protected UserUpdateEvent(Parcel in) {
        viewId = in.readInt();
        value = in.readString();
        title = in.readString();
    }

    public static final Creator<UserUpdateEvent> CREATOR = new Creator<UserUpdateEvent>() {
        @Override
        public UserUpdateEvent createFromParcel(Parcel in) {
            return new UserUpdateEvent(in);
        }

        @Override
        public UserUpdateEvent[] newArray(int size) {
            return new UserUpdateEvent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(viewId);
        parcel.writeString(value);
        parcel.writeString(title);
    }
}
