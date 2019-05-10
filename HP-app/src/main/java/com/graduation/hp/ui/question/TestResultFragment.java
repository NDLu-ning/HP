package com.graduation.hp.ui.question;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.core.utils.JsonUtils;
import com.graduation.hp.repository.http.entity.pojo.PhysiquePO;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class TestResultFragment extends BaseFragment {

    public static TestResultFragment newInstance(PhysiquePO physiquePO, boolean isCurUserLogin) {
        TestResultFragment fragment = new TestResultFragment();
        Bundle args = new Bundle();
        args.putParcelable(Key.RESULT, physiquePO);
        args.putBoolean(Key.IS_CURRENT_USER_LOGIN, isCurUserLogin);
        fragment.setArguments(args);
        return fragment;
    }

    private final String constitutionRegex = "您的体质是(.*?)。特点是:(.*?)。调养方式：(.*?)$";
    private final Pattern pattern = Pattern.compile(constitutionRegex);

    @BindView(R.id.test_result_whats_tv)
    AppCompatTextView testResultWhatsTv;

    @BindView(R.id.test_result_constitution_tv)
    AppCompatTextView testResultConstitutionTv;

    @BindView(R.id.test_result_fit_method_tv)
    AppCompatTextView testResultFitMethodTv;

    @BindView(R.id.test_result_constitution_description)
    AppCompatTextView testResultDescriptionTv;
    private TestResultFragmentListener mListener;


    @Override
    public void onAttach(Context context) {
        if (context instanceof TestResultFragmentListener) {
            mListener = (TestResultFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + "must be implements TestResultFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        initToolbar(view, getString(R.string.tips_test_result), R.mipmap.ic_close_white_32);
        Bundle bundle = getArguments();
        PhysiquePO result = bundle.getParcelable(Key.RESULT);
        boolean isCurUserLogin = bundle.getBoolean(Key.IS_CURRENT_USER_LOGIN);
        if (result == null) {
            throw new IllegalArgumentException("Result must be not null");
        }
        testResultWhatsTv.setText(getString(R.string.tips_whats_constitution, result.getTyped()));
        testResultConstitutionTv.setText(result.getTyped());
        testResultDescriptionTv.setText(result.getDetail());
        testResultFitMethodTv.setText(result.getRecuperate());

    }

    @OnClick(R.id.repeat_test)
    public void onClick() {
        mListener.repeatTest();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_result;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        getActivity().finish();
    }

    public interface TestResultFragmentListener {
        void repeatTest();
    }
}
