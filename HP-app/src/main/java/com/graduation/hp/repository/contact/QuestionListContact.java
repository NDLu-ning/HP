package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.repository.http.entity.wrapper.QuestionVOWrapper;

import java.util.ArrayList;
import java.util.List;

public interface QuestionListContact {

    interface View {

        void onGetQuestionsSuccess(ArrayList<QuestionVOWrapper> questions);

        void onCommitSuccess(String text);
    }

    interface Presenter {

        void getQuestionList(int type);

        void commitAnswer(List<AnswerPO> list);

        boolean isCurUserLogin();

    }
}
