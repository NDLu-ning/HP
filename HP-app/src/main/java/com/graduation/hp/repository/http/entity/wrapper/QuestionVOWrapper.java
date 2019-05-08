package com.graduation.hp.repository.http.entity.wrapper;

import android.os.Parcel;
import android.os.Parcelable;

import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.repository.http.entity.vo.QuestionVO;

public class QuestionVOWrapper implements Parcelable {

    private int no;
    private QuestionVO questionVO;
    private AnswerPO selectAnswerPO;
    private boolean enable;
    private boolean selected;

    public QuestionVOWrapper() {
    }

    public QuestionVOWrapper(int no, QuestionVO questionVO) {
        this.no = no;
        this.questionVO = questionVO;
        this.enable = false;
        this.selected = false;
    }

    public QuestionVOWrapper(int no, QuestionVO questionVO, AnswerPO selectAnswerPO, boolean enable, boolean selected) {
        this.no = no;
        this.questionVO = questionVO;
        this.selectAnswerPO = selectAnswerPO;
        this.enable = enable;
        this.selected = selected;
    }

    protected QuestionVOWrapper(Parcel in) {
        no = in.readInt();
        if (in.readByte() == 0) {
            questionVO = null;
        } else {
            questionVO = new QuestionVO(in);
        }
        if (in.readByte() == 0) {
            selectAnswerPO = null;
        } else {
            selectAnswerPO = new AnswerPO(in);
        }
        enable = in.readByte() != 0;
        selected = in.readByte() != 0;
    }

    public static final Creator<QuestionVOWrapper> CREATOR = new Creator<QuestionVOWrapper>() {
        @Override
        public QuestionVOWrapper createFromParcel(Parcel in) {
            return new QuestionVOWrapper(in);
        }

        @Override
        public QuestionVOWrapper[] newArray(int size) {
            return new QuestionVOWrapper[size];
        }
    };

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public QuestionVO getQuestionVO() {
        return questionVO;
    }

    public void setQuestionVO(QuestionVO questionVO) {
        this.questionVO = questionVO;
    }

    public AnswerPO getSelectAnswerPO() {
        return selectAnswerPO;
    }

    public void setSelectAnswerPO(AnswerPO selectAnswerPO) {
        this.selectAnswerPO = selectAnswerPO;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(no);
        if (questionVO == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeParcelable(questionVO, i);
        }
        if (selectAnswerPO == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeParcelable(selectAnswerPO, i);
        }
        parcel.writeByte((byte) (enable ? 1 : 0));
        parcel.writeByte((byte) (selected ? 1 : 0));
    }
}
