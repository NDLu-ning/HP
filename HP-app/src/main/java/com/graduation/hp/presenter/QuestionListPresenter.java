package com.graduation.hp.presenter;

import com.graduation.hp.R;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.QuestionListContact;
import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.repository.model.impl.QuestionModel;
import com.graduation.hp.ui.question.QuestionListFragment;

import java.util.List;

import javax.inject.Inject;

public class QuestionListPresenter extends BasePresenter<QuestionListFragment, QuestionModel>
        implements QuestionListContact.Presenter {

    @Inject
    public QuestionListPresenter(QuestionModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getQuestionList(int type) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showMessage(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpView.showLoading();
        mMvpModel.addSubscribe(mMvpModel.getAllQuestions(type)
                .subscribe(questions -> {
                    mMvpView.onGetQuestionsSuccess(questions);
                    mMvpView.showMain();
                }, throwable -> handlerApiError(throwable)));
    }

    @Override
    public void commitAnswer(List<AnswerPO> list) {

    }
}
