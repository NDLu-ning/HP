package com.graduation.hp.ui.provider;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.graduation.hp.R;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.utils.DateUtils;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.core.utils.ToastUtils;
import com.graduation.hp.repository.http.entity.CommentItem;
import com.graduation.hp.repository.http.entity.PostItem;
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

public class PostItemProvider extends ItemViewBinder<PostItem, PostItemProvider.ViewHolder> {

    private final OnItemClickListener mListener;

    public PostItemProvider(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_post_multi_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull PostItem item) {
        Resources resources = holder.itemView.getResources();
        GlideUtils.loadUserHead(holder.adapterPostIconIv, item.getAuthorIcon());
        holder.adapterPostNameTv.setText(item.getAuthor());
        holder.adapterPostContentTv.setText(item.getContent());
        holder.adapterPostReviewsTv.setText(String.format(resources.getString(R.string.tips_total_views_template), item.getViews()));
        holder.adapterPostLikeNumTv.setText(String.format(resources.getString(R.string.tips_total_like_count_template), item.getLikeCount()));
        holder.adapterPostDateTv.setText(DateUtils.formatPublishDate(item.getCreated()));
        holder.adapterPostTagTv.setText(BeanFactory.constitutions[Integer.parseInt(item.getConstitution())]);
        holder.adapterPostTagTv.setTextColor(resources.getColor(BeanFactory.constitutions_color[Integer.parseInt(item.getConstitution())]));
        holder.adapterPostTagTv.setBackgroundResource(BeanFactory.constitutions_bg_res[Integer.parseInt(item.getConstitution())]);
        holder.adapterPostHotIv.setVisibility(View.VISIBLE);
        List<ImageInfo> imageInfos = new ArrayList<>();
        String[] images = item.getImages().split(",");
        for (String image : images) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setBigImageUrl(image);
            imageInfo.setThumbnailUrl(image);
            imageInfos.add(imageInfo);
        }
        holder.adapterPostImageContainer.setAdapter(new NineGridViewClickAdapter(holder.itemView.getContext(), imageInfos));
        holder.adapterPostImageContainer.setVisibility(images.length == 0 ? View.GONE : View.VISIBLE);
        List<CommentItem> commentItems = item.getCommentItems();
        if (commentItems != null && commentItems.size() > 0) {
            holder.adapterPostCommentContainer.setVisibility(View.VISIBLE);
            holder.adapterPostMoreTv.setVisibility(commentItems.size() > 15 ? View.VISIBLE : View.GONE);
            holder.adapterPostMoreTv.setOnClickListener(v -> {
                ToastUtils.show(holder.itemView.getContext(), "More Click");
            });
            int showSize = commentItems.size() > 15 ? 15 : commentItems.size();
            for (int i = 0; i < showSize; i++) {
                holder.adapterPostCommentContainer.addView(createCommentView(holder.itemView.getContext(), commentItems.get(i)));
            }
        }
    }

    private View createCommentView(Context context, CommentItem commentItem) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_post_reply_item, null);
        AppCompatTextView replyTv = rootView.findViewById(R.id.adapter_post_reply_tv);
        String content;
        if (!TextUtils.isEmpty(commentItem.getReplyUserName())) {
            content = String.format(context.getString(R.string.comment_has_reply_object), commentItem.getCommentUserName(), commentItem.getReplyUserName(), commentItem.getContent());
        } else {
            content = String.format(context.getString(R.string.comment_has_no_reply_object), commentItem.getCommentUserName(), commentItem.getContent());
        }
        replyTv.setText(Html.fromHtml(content));
        return replyTv;
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
        @BindView(R.id.adapter_post_like_btn)
        LikeButton adapterPostLikeBtn;
        @BindView(R.id.adapter_post_like_num_tv)
        AppCompatTextView adapterPostLikeNumTv;
        @BindView(R.id.adapter_post_comment_container)
        LinearLayout adapterPostCommentContainer;
        @BindView(R.id.adapter_post_more_tv)
        AppCompatTextView adapterPostMoreTv;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
