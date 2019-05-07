package com.graduation.hp.ui.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.hp.R;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.repository.http.entity.pojo.FocusPO;
import com.graduation.hp.utils.StringUtils;
import com.graduation.hp.widget.AttentionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class AttentionItemProvider extends ItemViewBinder<FocusPO, AttentionItemProvider.ViewHolder> {

    private final AttentionItemClickListener mListener;

    public AttentionItemProvider(AttentionItemProvider.AttentionItemClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_attention_user_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull FocusPO item) {

        GlideUtils.loadUserHead(holder.adapterAttentionTabIconIv, item.getHeadUrl());
        holder.adapterAttentionItemNameTv.setText(item.getNickname());
        holder.adapterAttentionItemRemarkTv.setText(item.getRemark());
        holder.adapterAttentionItemArticleTv.setText(StringUtils.getFormattedOverMaximumString(item.getArticleNum(), 99, R.string.tips_over_maximum_articles));
        holder.adapterAttentionItemSubBtn.setFocusOn(true);
        holder.adapterAttentionItemSubBtn.setAttentionButtonClickListener((v, focusOn) -> {
            if (mListener != null) {
                mListener.onAttentionCbClick(item.getAuthorId(), focusOn);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(item.getAuthorId());
            }
        });
    }

    public interface AttentionItemClickListener {
        void onAttentionCbClick(long authorId, boolean focusOn);

        void onItemClick(long authorId);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.adapter_attention_item_image_iv)
        AppCompatImageView adapterAttentionTabIconIv;

        @BindView(R.id.adapter_attention_item_name_tv)
        AppCompatTextView adapterAttentionItemNameTv;

        @BindView(R.id.adapter_attention_item_article_tv)
        AppCompatTextView adapterAttentionItemArticleTv;

        @BindView(R.id.adapter_attention_item_remark_tv)
        AppCompatTextView adapterAttentionItemRemarkTv;

        @BindView(R.id.adapter_attention_item_sub_btn)
        AttentionButton adapterAttentionItemSubBtn;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
