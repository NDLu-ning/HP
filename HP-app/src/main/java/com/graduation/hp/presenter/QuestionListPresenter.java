package com.graduation.hp.presenter;

import android.text.TextUtils;

import com.graduation.hp.R;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.contact.QuestionListContact;
import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.repository.model.impl.QuestionModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;
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
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showMessage(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpView.showDialog(HPApplication.getStringById(R.string.tips_upload_ing));
        mMvpModel.addSubscribe(mMvpModel.commit(list)
                .doFinally(() -> mMvpView.dismissDialog())
                .subscribe(result -> {
                            RepositoryHelper repositoryHelper = mMvpModel.getRepositoryHelper();
                            PreferencesHelper preferencesHelper = repositoryHelper.getPreferencesHelper();
                            if (isCurUserLogin()) {
                                preferencesHelper.saveCurrentUserPhysiquId(result.getId());
                            }
                            preferencesHelper.saveTestResult(JsonUtils.objectToJson(result));
                            mMvpView.onCommitSuccess(result);

                        },
                        throwable -> handlerApiError(throwable)));
    }

    @Override
    public boolean isCurUserLogin() {
        RepositoryHelper repositoryHelper = mMvpModel.getRepositoryHelper();
        if (repositoryHelper == null) return false;
        PreferencesHelper preferencesHelper = repositoryHelper.getPreferencesHelper();
        if (preferencesHelper == null) return false;
        return !TextUtils.isEmpty(preferencesHelper.getCurrentUserToken());
    }
}
