package com.graduation.hp.repository.http.entity.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.graduation.hp.repository.http.entity.pojo.QuestionPO;

public class QuestionVO extends QuestionPO implements Parcelable {

    /*
     * 选项
     */
    private String option;
    private String option2;
    private String option3;
    private String option4;
    private String option5;

    public QuestionVO() {
    }

    public QuestionVO(Parcel in) {
        super(in);
        option = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        option5 = in.readString();
    }

    public static final Creator<QuestionVO> CREATOR = new Creator<QuestionVO>() {
        @Override
        public QuestionVO createFromParcel(Parcel in) {
            return new QuestionVO(in);
        }

        @Override
        public QuestionVO[] newArray(int size) {
            return new QuestionVO[size];
        }
    };

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getOption5() {
        return option5;
    }

    public void setOption5(String option5) {
        this.option5 = option5;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(option);
        parcel.writeString(option2);
        parcel.writeString(option3);
        parcel.writeString(option4);
        parcel.writeString(option5);
    }
}
