package com.graduation.hp.ui.question;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.graduation.hp.R;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.RootFragment;

public class QuestionListFragment extends RootFragment {

    interface QuestionListFragmentListener {

        void getQuestionList();

        void commitAnswers();
    }


    public static QuestionListFragment newInstance() {
        QuestionListFragment fragment = new QuestionListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected boolean shouldShowNoDataView() {
        return true;
    }

    @Override
    protected int getNoDataStringResId() {
        return R.string.tips_empty_question_list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_question_list;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }
}
