package com.graduation.hp.core.repository.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.graduation.hp.core.utils.CommonUtils;

public class BottomSheetOption implements Parcelable {

    public static final Creator<BottomSheetOption> CREATOR = new Creator<BottomSheetOption>() {
        @Override
        public BottomSheetOption createFromParcel(Parcel source) {
            return new BottomSheetOption(source);
        }

        @Override
        public BottomSheetOption[] newArray(int size) {
            return new BottomSheetOption[size];
        }
    };

    private final int mId;
    private final String mText;
    private final boolean mShowHighlightBadge;
    private final String mSecondaryText;

    public BottomSheetOption(int id, String text) {
        this(id, text, false);
    }

    public BottomSheetOption(int id, String text, boolean showHighlightBadge) {
        this(id, text, showHighlightBadge, null);
    }

    public BottomSheetOption(int id, String text, String secondaryText) {
        this(id, text, false, secondaryText);
    }

    public BottomSheetOption(int id, String text, boolean showHighlightBadge, String secondaryText) {
        mId = id;
        mText = text;
        mShowHighlightBadge = showHighlightBadge;
        mSecondaryText = secondaryText;
    }

    private BottomSheetOption(Parcel in) {
        mId = in.readInt();
        mText = in.readString();
        mShowHighlightBadge = CommonUtils.toBoolean(in.readInt());
        mSecondaryText = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mText);
        dest.writeInt(CommonUtils.toInt(mShowHighlightBadge));
        dest.writeString(mSecondaryText);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }

    public boolean shouldShowHighlightBadge() {
        return mShowHighlightBadge;
    }

    public String getSecondaryText() {
        return mSecondaryText;
    }
}
