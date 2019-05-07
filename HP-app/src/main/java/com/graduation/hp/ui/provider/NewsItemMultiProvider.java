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
import android.widget.LinearLayout;

import com.graduation.hp.R;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.utils.DateUtils;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.repository.http.entity.vo.ArticleVO;

import me.drakeet.multitype.ItemViewBinder;

public class NewsItemMultiProvider extends ItemViewBinder<ArticleVO, NewsItemMultiProvider.ViewHolder> {
    private final OnItemClickListener mListener;

    public NewsItemMultiProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_news_multi_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ArticleVO item) {
        Resources resources = holder.itemView.getResources();
        holder.adapterNewsMultiTitleTv.setText(Html.fromHtml(item.getTitle()));
        holder.adapterNewsMultiCommentTv.setText(resources.getString(R.string.news_comment_num_template, item.getDiscussNum()));
        holder.adapterNewsMultiDateTv.setText(DateUtils.formatPublishDate(item.getCreateTime()));
        holder.adapterNewsMultiAuthorTv.setText(item.getNickname());
        final String image = item.getImages();
        if (!TextUtils.isEmpty(image)) {
            holder.adapterNewsMultiLl.setVisibility(View.VISIBLE);
            String[] images = image.split(",");
            for (int i = 0; i < 3; i++) {
                holder.adapterNewsImagesIv[i].setVisibility(View.VISIBLE);
                GlideUtils.loadImage(holder.adapterNewsImagesIv[i], images[i]);
            }
        }
        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.OnItemClick(holder.itemView, item, holder.getAdapterPosition());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView adapterNewsMultiTitleTv;
        LinearLayout adapterNewsMultiLl;
        AppCompatImageView[] adapterNewsImagesIv;
        AppCompatTextView adapterNewsMultiDateTv;
        AppCompatTextView adapterNewsMultiAuthorTv;
        AppCompatTextView adapterNewsMultiCommentTv;

        ViewHolder(View itemView) {
            super(itemView);
            adapterNewsImagesIv = new AppCompatImageView[3];
            adapterNewsMultiLl = itemView.findViewById(R.id.adapter_news_multi_ll);
            adapterNewsMultiTitleTv = itemView.findViewById(R.id.adapter_news_multi_title_tv);
            adapterNewsImagesIv[0] = itemView.findViewById(R.id.adapter_news_image_1_tv);
            adapterNewsImagesIv[1] = itemView.findViewById(R.id.adapter_news_image_2_tv);
            adapterNewsImagesIv[2] = itemView.findViewById(R.id.adapter_news_image_3_tv);
            adapterNewsMultiAuthorTv = itemView.findViewById(R.id.adapter_news_multi_author_tv);
            adapterNewsMultiDateTv = itemView.findViewById(R.id.adapter_news_multi_date_tv);
            adapterNewsMultiCommentTv = itemView.findViewById(R.id.adapter_news_multi_comment_tv);
        }
    }
}
