package com.graduation.hp.ui.provider;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.hp.R;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.utils.DateUtils;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.repository.http.entity.ArticleVO;
import com.graduation.hp.repository.http.entity.InvitationVO;

import me.drakeet.multitype.ItemViewBinder;

public class ConstitutionItemBigProvider extends ItemViewBinder<InvitationVO, ConstitutionItemBigProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public ConstitutionItemBigProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_news_big_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull InvitationVO item) {
        Resources resources = holder.itemView.getResources();
        holder.adapterNewsBigAuthorTv.setText(item.getNickname());
        holder.adapterNewsBigCommentTv.setText(resources.getString(R.string.news_comment_num_template, item.getDiscussNum()));
        holder.adapterNewsBigDateTv.setText(DateUtils.formatPublishDate(item.getCreateTime()));
        holder.adapterNewsBigTitleTv.setText(Html.fromHtml(item.getTitle()));
        String image = item.getImages();
        if (TextUtils.isEmpty(image)) {
            holder.adapterNewsBigImageIv.setVisibility(View.GONE);
        } else {
            String[] images = image.split(",");
            GlideUtils.loadImage(holder.adapterNewsBigImageIv, images[0]);
        }
        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.OnItemClick(holder.itemView, item, holder.getAdapterPosition());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView adapterNewsBigTitleTv;
        AppCompatImageView adapterNewsBigImageIv;
        AppCompatTextView adapterNewsBigDateTv;
        AppCompatTextView adapterNewsBigAuthorTv;
        AppCompatTextView adapterNewsBigCommentTv;

        ViewHolder(View itemView) {
            super(itemView);
            adapterNewsBigTitleTv = itemView.findViewById(R.id.adapter_news_big_title_tv);
            adapterNewsBigImageIv = itemView.findViewById(R.id.adapter_news_big_image_iv);
            adapterNewsBigDateTv = itemView.findViewById(R.id.adapter_news_big_date_tv);
            adapterNewsBigAuthorTv = itemView.findViewById(R.id.adapter_news_big_author_tv);
            adapterNewsBigCommentTv = itemView.findViewById(R.id.adapter_news_big_comment_tv);
        }
    }
}
