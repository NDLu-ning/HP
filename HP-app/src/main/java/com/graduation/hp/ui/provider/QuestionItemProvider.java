package com.graduation.hp.ui.provider;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.graduation.hp.R;
import com.graduation.hp.repository.http.entity.pojo.AnswerPO;
import com.graduation.hp.repository.http.entity.wrapper.QuestionVOWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class QuestionItemProvider extends ItemViewBinder<QuestionVOWrapper, QuestionItemProvider.ViewHolder> {

    private QuestionItemProviderListener mListener;

    public QuestionItemProvider(QuestionItemProviderListener listener) {
        this.mListener = listener;
    }

    public interface QuestionItemProviderListener {

        void onSelectedQuestion(int no);

        void onSelectAnswer(int no, String answer);
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_question_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull QuestionVOWrapper item) {
        holder.adapterQuestionContainerCl.setBackgroundResource(!item.isEnable() ? R.drawable.bg_question_not_enable :
                (item.isSelected() ? R.drawable.bg_question_selected : R.drawable.bg_question_normal));
        holder.adapterQuestionNoTv.setText(item.getNo() + "");
        holder.adapterQuestionNoTv.setEnabled(item.isEnable());
        holder.adapterQuestionContentTv.setEnabled(item.isEnable());
        holder.adapterQuestionContentTv.setText(item.getQuestionVO().getContext());
        holder.adapterAnswerContainerRg.setVisibility(item.isEnable() && item.isSelected() ? View.VISIBLE : View.GONE);
        if (item.getSelectAnswerPO() != null) {
            holder.adapterQuestionSelectedTv.setVisibility(View.VISIBLE);
            holder.adapterQuestionSelectedTv.setText(item.getSelectAnswerPO().getAnswerContext());
        }
        for (int i = 0; i < 5; i++) {
            RadioButton radioButton = holder.adapterAnswerItems[i];
            AnswerPO answerPO = item.getSelectAnswerPO();
            if (answerPO != null) {
                radioButton.setChecked(radioButton.getText().toString().equals(answerPO.getAnswerContext()));
            } else {
                radioButton.setChecked(false);
            }
        }
        holder.itemView.setOnClickListener(v -> {
            if (!item.isEnable()) {
                return;
            }
            if (mListener != null) {
                mListener.onSelectedQuestion(item.getNo());
            }
        });
        holder.adapterAnswerContainerRg.setOnCheckedChangeListener((group, checkedId) -> {
            if (!item.isEnable() || !item.isSelected()) return;
            if (mListener != null) {
                mListener.onSelectAnswer(item.getNo(), ((RadioButton) holder.itemView.findViewById(checkedId)).getText().toString());
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.adapter_question_container_cl)
        ConstraintLayout adapterQuestionContainerCl;

        @BindView(R.id.adapter_answer_container_rg)
        RadioGroup adapterAnswerContainerRg;

        @BindView(R.id.adapter_question_no_tv)
        AppCompatTextView adapterQuestionNoTv;

        @BindView(R.id.adapter_question_content_tv)
        AppCompatTextView adapterQuestionContentTv;

        @BindView(R.id.adapter_question_selected_tv)
        AppCompatTextView adapterQuestionSelectedTv;

        RadioButton[] adapterAnswerItems;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            adapterAnswerItems = new RadioButton[5];
            adapterAnswerItems[0] = itemView.findViewById(R.id.adapter_answer_never_item);
            adapterAnswerItems[1] = itemView.findViewById(R.id.adapter_answer_less_item);
            adapterAnswerItems[2] = itemView.findViewById(R.id.adapter_answer_sometimes_item);
            adapterAnswerItems[3] = itemView.findViewById(R.id.adapter_answer_usually_item);
            adapterAnswerItems[4] = itemView.findViewById(R.id.adapter_answer_always_item);
        }
    }
}
