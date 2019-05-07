package com.graduation.hp.repository.http.entity.wrapper;

import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.repository.http.entity.vo.QuestionVO;

public class QuestionVOWrapper {

    private int no;
    private QuestionVO questionVO;
    private AnswerPO selectAnswerPO;
    private boolean enable;
    private boolean selected;

    public QuestionVOWrapper() {
    }

    public QuestionVOWrapper(int no,QuestionVO questionVO, AnswerPO selectAnswerPO, boolean enable, boolean selected) {
        this.no = no;
        this.questionVO = questionVO;
        this.selectAnswerPO = selectAnswerPO;
        this.enable = enable;
        this.selected = selected;
    }

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
}
