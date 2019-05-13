package com.graduation.hp.ui.question;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.core.utils.ToastUtils;
import com.graduation.hp.presenter.QuestionListPresenter;
import com.graduation.hp.repository.contact.QuestionListContact;
import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.repository.http.entity.pojo.PhysiquePO;
import com.graduation.hp.repository.http.entity.wrapper.QuestionVOWrapper;
import com.graduation.hp.ui.auth.reset.InputPhoneFragment;
import com.graduation.hp.ui.provider.QuestionItemProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class QuestionListFragment extends RootFragment<QuestionListPresenter>
        implements QuestionListContact.View {

    public static QuestionListFragment newInstance(int type) {
        QuestionListFragment fragment = new QuestionListFragment();
        Bundle args = new Bundle();
        args.putInt(Key.TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    Items mItems;

    @Inject
    MultiTypeAdapter mAdapter;

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    private ArrayList<QuestionVOWrapper> mQuestionList;
    private int mType;
    private QuestionListFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        try {
            mListener = (QuestionListFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement InputPhoneFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        if (savedInstanceState != null) {
            mType = savedInstanceState.getInt(Key.TYPE, 0);
            mQuestionList = savedInstanceState.getParcelableArrayList(Key.QUESTION_LIST);
        } else {
            Bundle args = getArguments();
            mType = args.getInt(Key.TYPE, 0);
        }
        initToolbar(rootView, getString(R.string.tips_constitution_test_title), R.mipmap.ic_navigation_back_white, getString(R.string.action_commit), R.color.md_white);
        initMultiTypeAdapter();
    }

    private void initMultiTypeAdapter() {
        mAdapter.register(QuestionVOWrapper.class, new QuestionItemProvider(listener));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onLazyLoad() {
        if (mQuestionList != null) {
            onGetQuestionsSuccess(mQuestionList);
        } else {
            mPresenter.getQuestionList(mType);
        }
    }

    private QuestionItemProvider.QuestionItemProviderListener listener = new QuestionItemProvider.QuestionItemProviderListener() {
        private int mLatestNo = 1;

        @Override
        public void onSelectedQuestion(int no) {
            if (mQuestionList != null) {
                for (QuestionVOWrapper wrapper : mQuestionList) {
                    if (wrapper.getNo() == no) {
                        wrapper.setEnable(true);
                        wrapper.setSelected(true);
                    } else if (wrapper.getNo() < no) {
                        wrapper.setEnable(true);
                        wrapper.setSelected(false);
                    } else {
                        wrapper.setEnable(wrapper.getSelectAnswerPO() != null);
                        wrapper.setSelected(false);
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(no);
        }

        @Override
        public void onSelectAnswer(int no, String answer) {
            mLatestNo = mLatestNo - 1 > no ? mLatestNo : no + 1;
            if (mQuestionList != null) {
                for (int i = 0; i < mQuestionList.size(); i++) {
                    QuestionVOWrapper wrapper = mQuestionList.get(i);
                    if (wrapper.getNo() == no) {
                        wrapper.setSelectAnswerPO(new AnswerPO(answer));
                        wrapper.setEnable(true);
                        wrapper.setSelected(false);
                    } else if (wrapper.getNo() <= mLatestNo) {
                        wrapper.setSelected(wrapper.getNo() == mLatestNo);
                        wrapper.setEnable(true);
                    } else {
                        wrapper.setEnable(wrapper.getSelectAnswerPO() != null);
                        wrapper.setSelected(false);
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(mLatestNo);
        }
    };

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mQuestionList != null) {
            outState.putParcelableArrayList(Key.QUESTION_LIST, mQuestionList);
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onGetQuestionsSuccess(ArrayList<QuestionVOWrapper> wrapperList) {
        if (!isAdded()) return;
        this.mQuestionList = wrapperList;
        mItems.clear();
        mItems.addAll(wrapperList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCommitSuccess(PhysiquePO physiquePO) {
        if (!isAdded()) return;
        mListener.commitAnswersSuccess(physiquePO, mPresenter.isCurUserLogin());
    }

    @Override
    public void onToolbarLeftClickListener(View v) {
        ((QuestionActivity) getActivity()).onToolbarLeftClickListener(v);
    }

    @Override
    public void onToolbarRightClickListener(View v) {
        if (!isAdded() || mQuestionList == null || mQuestionList.size() == 0) {
            ToastUtils.show(getContext(), getString(R.string.tips_donot_commit_this_time));
            return;
        }
        List<AnswerPO> list = new ArrayList<>();
        for (int i = 0; i < mQuestionList.size(); i++) {
            QuestionVOWrapper wrapper = mQuestionList.get(i);
            if (wrapper.getSelectAnswerPO() == null) {
                ToastUtils.show(getContext(), getString(R.string.tips_have_not_finish, wrapper.getNo()));
                break;
            }
            list.add(wrapper.getSelectAnswerPO());
        }
        if (list.size() == mQuestionList.size()) {
            mPresenter.commitAnswer(list);
        }
    }

    public interface QuestionListFragmentListener {
        void commitAnswersSuccess(PhysiquePO physiquePO, boolean isCurUserLogin);
    }
}
