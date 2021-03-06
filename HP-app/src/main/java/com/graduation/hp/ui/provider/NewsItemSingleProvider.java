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
import com.graduation.hp.core.app.listener.SimpleItemClickListenerAdapter;
import com.graduation.hp.core.utils.DateUtils;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.repository.http.entity.NewsList;

import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class NewsItemSingleProvider extends ItemViewBinder<NewsList, NewsItemSingleProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public NewsItemSingleProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_news_single_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull NewsList item) {
        Resources resources = holder.itemView.getResources();
        holder.adapterNewsTitleTv.setText(Html.fromHtml(item.getTitle()));
        holder.adapterNewsDateTv.setText(DateUtils.formatPublishDate(item.getDate()));
        holder.adapterNewsSingleCommentTv.setText(resources.getString(R.string.news_comment_num_template, item.getComment()));
        holder.adapterNewsSingleAuthorTv.setText(item.getAuthor());
        String image = item.getImages();
        if (TextUtils.isEmpty(image)) {
            holder.adapterNewsIsVideoIv.setVisibility(View.GONE);
            holder.adapterNewsImageIv.setVisibility(View.GONE);
        } else {
            String[] images = image.split(",");
            LogUtils.d(image);
            holder.adapterNewsIsVideoIv.setVisibility(item.isVideo() ? View.VISIBLE : View.GONE);
            GlideUtils.loadImage(holder.adapterNewsImageIv, images[0]);
        }
        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.OnItemClick(holder.itemView, item, holder.getAdapterPosition());
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView adapterNewsIsVideoIv;
        AppCompatTextView adapterNewsTitleTv;
        AppCompatImageView adapterNewsImageIv;
        AppCompatTextView adapterNewsDateTv;
        AppCompatTextView adapterNewsSingleAuthorTv;
        AppCompatTextView adapterNewsSingleCommentTv;

        ViewHolder(View itemView) {
            super(itemView);
            adapterNewsIsVideoIv = itemView.findViewById(R.id.adapter_news_single_isvideo_iv);
            adapterNewsTitleTv = itemView.findViewById(R.id.adapter_news_single_title_tv);
            adapterNewsImageIv = itemView.findViewById(R.id.adapter_news_single_image_iv);
            adapterNewsDateTv = itemView.findViewById(R.id.adapter_news_single_date_tv);
            adapterNewsSingleAuthorTv = itemView.findViewById(R.id.adapter_news_single_author_tv);
            adapterNewsSingleCommentTv = itemView.findViewById(R.id.adapter_news_single_comment_tv);
        }
    }
}
