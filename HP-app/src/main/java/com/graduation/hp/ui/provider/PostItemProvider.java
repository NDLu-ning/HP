package com.graduation.hp.ui.provider;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.hp.R;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.utils.DateUtils;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.repository.http.entity.vo.InvitationVO;
import com.graduation.hp.utils.BeanFactory;
import com.graduation.hp.widget.LikeButton;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

public class PostItemProvider extends ItemViewBinder<InvitationVO, PostItemProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public PostItemProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_post_tab_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull InvitationVO item) {
        Resources resources = holder.itemView.getResources();
        GlideUtils.loadUserHead(holder.adapterPostIconIv, item.getHeadUrl());
        holder.adapterPostNameTv.setText(item.getNickname());
        holder.adapterPostDateTv.setText(DateUtils.formatPublishDate(item.getCreateTime()));
        int index = Integer.parseInt(String.valueOf(item.getPhysiqueId()));
        index = index - 1 >= 0 ? index - 1 : 0;
        holder.adapterPostTagTv.setText(BeanFactory.constitutions[index]);
        holder.adapterPostTagTv.setTextColor(resources.getColor(BeanFactory.constitutions_color[index]));
        holder.adapterPostTagTv.setBackgroundResource(BeanFactory.constitutions_bg_res[index]);
        holder.adapterPostHotIv.setVisibility(View.VISIBLE);
        holder.adapterPostContentTv.setText(item.getContext());
        holder.adapterPostReviewsTv.setText(String.format(resources.getString(R.string.tips_total_views_template), item.getLikeNum()));
        holder.adapterPostLikeBtn.setLiked(false);
        holder.adapterPostLikeNumTv.setText(String.valueOf(item.getLikeNum()));
        holder.adapterPostCommentNumTv.setText(String.valueOf(item.getDiscussNum()));
        holder.adapterPostHotIv.setVisibility(item.getLikeNum() > 1 ? View.VISIBLE : View.GONE);
        List<ImageInfo> imageInfos = new ArrayList<>();
        String[] images = item.getImages().split(",");
        for (int i = 0; i < images.length; i++) {
            String image = images[i];
            if (TextUtils.isEmpty(image)) {
                continue;
            }
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setBigImageUrl(image);
            imageInfo.setThumbnailUrl(image);
            imageInfos.add(imageInfo);
        }
        holder.adapterPostImageContainer.setAdapter(new NineGridViewClickAdapter(holder.itemView.getContext(), imageInfos));
        holder.adapterPostImageContainer.setVisibility(images.length == 0 ? View.GONE : View.VISIBLE);
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.OnItemClick(holder.itemView, item);
            }
        });
        holder.adapterPostLikeBtn.setLikeButtonClickListener((v, isLiked) -> {
            if (mListener != null) {
                holder.adapterPostLikeBtn.setLiked(!isLiked);
                item.setLikeNum(!isLiked ? item.getLikeNum() + 1 : item.getLikeNum() - 1);
                holder.adapterPostLikeNumTv.setText(String.valueOf(item.getLikeNum()));
                mListener.OnItemCheckChange(holder.itemView, item, isLiked);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.adapter_post_icon_iv)
        AppCompatImageView adapterPostIconIv;
        @BindView(R.id.adapter_post_name_tv)
        AppCompatTextView adapterPostNameTv;
        @BindView(R.id.adapter_post_date_tv)
        AppCompatTextView adapterPostDateTv;
        @BindView(R.id.adapter_post_tag_tv)
        AppCompatTextView adapterPostTagTv;
        @BindView(R.id.adapter_post_hot_iv)
        AppCompatImageView adapterPostHotIv;
        @BindView(R.id.adapter_post_content_tv)
        AppCompatTextView adapterPostContentTv;
        @BindView(R.id.adapter_post_image_container)
        NineGridView adapterPostImageContainer;
        @BindView(R.id.adapter_post_reviews_tv)
        AppCompatTextView adapterPostReviewsTv;
        @BindView(R.id.adapter_post_comment_iv)
        AppCompatImageView adapterPostCommentIv;
        @BindView(R.id.adapter_post_comment_num_tv)
        AppCompatTextView adapterPostCommentNumTv;

        @BindView(R.id.adapter_post_like_btn)
        LikeButton adapterPostLikeBtn;
        @BindView(R.id.adapter_post_like_num_tv)
        AppCompatTextView adapterPostLikeNumTv;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
