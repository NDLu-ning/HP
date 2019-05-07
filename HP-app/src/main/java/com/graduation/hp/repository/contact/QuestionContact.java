package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.ui.question.QuestionListFragment;

import java.util.List;

public interface QuestionContact {

    interface View extends QuestionListFragment.QuestionListFragmentListener {


    }

    interface Presenter {

        void getQuestionList(int type);

        void commitAnswer(List<AnswerPO> list);

    }
}
