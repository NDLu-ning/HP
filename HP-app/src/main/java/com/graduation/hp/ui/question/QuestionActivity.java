package com.graduation.hp.ui.question;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.graduation.hp.app.di.component.DaggerActivityComponent;
import com.graduation.hp.app.di.module.ActivityModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.presenter.QuestionPresenter;
import com.graduation.hp.repository.contact.QuestionContact;

public class QuestionActivity extends SingleFragmentActivity<QuestionPresenter>
        implements QuestionContact.View {


    @Override
    protected Fragment createMainContentFragment() {
        return new QuestionListFragment();
    }

    @Override
    public void getQuestionList() {

    }

    @Override
    public void commitAnswers() {

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }
}
