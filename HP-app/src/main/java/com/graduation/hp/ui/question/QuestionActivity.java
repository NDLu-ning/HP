package com.graduation.hp.ui.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.SingleFragmentActivity;
import com.graduation.hp.repository.http.entity.pojo.PhysiquePO;

public class QuestionActivity extends SingleFragmentActivity
        implements QuestionListFragment.QuestionListFragmentListener {

    public static final int FRAGMENT_IS_QUESTION_LIST = 1;
    public static final int FRAGMENT_IS_QUESTION_RESULT = 2;
    public static final int FRAGMENT_IS_QUESTION_WELCOME = 3;

    public static Intent createIntent(Context context, int type) {
        return createIntent(context, type, FRAGMENT_IS_QUESTION_LIST);
    }

    public static Intent createIntent(Context context, int type, int curFragment) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.putExtra(Key.TYPE, type);
        intent.putExtra(Key.CUR_FRAGMENT, curFragment);
        return intent;
    }

    private int mType;
    private int mCurFragment;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent intent = getIntent();
        mType = intent.getIntExtra(Key.TYPE, 0);
        mCurFragment = intent.getIntExtra(Key.CUR_FRAGMENT, FRAGMENT_IS_QUESTION_LIST);
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_question;
    }

    @Override
    protected Fragment createMainContentFragment() {
        switch (mCurFragment) {
            case FRAGMENT_IS_QUESTION_LIST:
            default:
                return QuestionListFragment.newInstance(mType);
        }
    }

    @Override
    public void commitAnswersSuccess(PhysiquePO physiquePO, boolean isCurUserLogin) {
        replaceMainContentFragment(TestResultFragment.newInstance(physiquePO, isCurUserLogin), false);
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
    }
}
