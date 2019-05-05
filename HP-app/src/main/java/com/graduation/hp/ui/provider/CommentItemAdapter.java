package com.graduation.hp.ui.provider;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.res.Resources;

import com.graduation.hp.R;
import com.graduation.hp.repository.http.entity.ArticleDiscussPO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.ViewHolder> {

    public interface CommentItemAdapterListener {
        void onReplyClick(long articleId, long talkUserId, String talkUserNickname, String talkContent);
    }

    private final CommentItemAdapterListener mListener;
    private final List<ArticleDiscussPO> mList;
    private Context mContext;

    public CommentItemAdapter(Context context, List<ArticleDiscussPO> list, CommentItemAdapterListener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_post_reply_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArticleDiscussPO articleDiscussPO = mList.get(position);
        Resources resources = holder.itemView.getResources();
        if (articleDiscussPO == null) return;
        if (articleDiscussPO.getDiscussType() == 1) {
            holder.adapterPostReplyTv.setText(Html.fromHtml(resources.getString(R.string.comment_has_no_reply_object, articleDiscussPO.getNickname(), articleDiscussPO.getContext())));
        } else {
            holder.adapterPostReplyTv.setText(Html.fromHtml(resources.getString(R.string.comment_has_reply_object, articleDiscussPO.getNickname(),
                    articleDiscussPO.getTalkNickname(), articleDiscussPO.getContext())));
        }
        holder.adapterPostReplyTv.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onReplyClick(articleDiscussPO.getArticleId(), articleDiscussPO.getUserId(),
                        articleDiscussPO.getNickname(), articleDiscussPO.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.adapter_post_reply_tv)
        AppCompatTextView adapterPostReplyTv;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
