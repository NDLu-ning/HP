package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.QuestionContact;
import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.repository.model.impl.QuestionModel;
import com.graduation.hp.ui.question.QuestionActivity;

import java.util.List;

import javax.inject.Inject;

public class QuestionPresenter extends BasePresenter<QuestionActivity, QuestionModel>
        implements QuestionContact.Presenter {

    @Inject
    public QuestionPresenter(QuestionModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getQuestionList(int type) {

    }

    @Override
    public void commitAnswer(List<AnswerPO> list) {

    }
}
