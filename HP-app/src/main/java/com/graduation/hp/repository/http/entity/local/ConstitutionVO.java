package com.graduation.hp.repository.http.entity.local;

import android.os.Parcel;
import android.os.Parcelable;

public class ConstitutionVO implements Parcelable {

    private long id;
    private String type;
    private String detail;

    public ConstitutionVO() {
    }

    public ConstitutionVO(long id, String type, String detail) {
        this.id = id;
        this.type = type;
        this.detail = detail;
    }

    protected ConstitutionVO(Parcel in) {
        id = in.readLong();
        type = in.readString();
        detail = in.readString();
    }

    public static final Creator<ConstitutionVO> CREATOR = new Creator<ConstitutionVO>() {
        @Override
        public ConstitutionVO createFromParcel(Parcel in) {
            return new ConstitutionVO(in);
        }

        @Override
        public ConstitutionVO[] newArray(int size) {
            return new ConstitutionVO[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(type);
        parcel.writeString(detail);
    }
}
