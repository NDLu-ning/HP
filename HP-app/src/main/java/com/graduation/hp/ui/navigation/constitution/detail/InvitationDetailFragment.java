package com.graduation.hp.ui.navigation.constitution.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.app.event.DiscussEvent;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.core.utils.DateUtils;
import com.graduation.hp.core.utils.GlideUtils;
import com.graduation.hp.core.utils.LogUtils;
import com.graduation.hp.presenter.InvitationDetailPresenter;
import com.graduation.hp.presenter.NewsDetailPresenter;
import com.graduation.hp.repository.contact.InvitationDetailContact;
import com.graduation.hp.repository.contact.NewsDetailContact;
import com.graduation.hp.repository.http.entity.ArticleVO;
import com.graduation.hp.repository.http.entity.InvitationVO;
import com.graduation.hp.ui.navigation.news.comment.NewsCommentFragment;
import com.graduation.hp.ui.navigation.news.comment.PrepareForDiscussionListener;
import com.graduation.hp.ui.navigation.user.center.UserCenterActivity;
import com.graduation.hp.utils.StringUtils;
import com.graduation.hp.widget.AttentionButton;
import com.graduation.hp.widget.LikeButton;
import com.graduation.hp.widget.dialog.CommentDialog;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.Body;

public class InvitationDetailFragment extends RootFragment<InvitationDetailPresenter>
        implements InvitationDetailContact.View {

    public static InvitationDetailFragment newInstance(long invitationId) {
        InvitationDetailFragment fragment = new InvitationDetailFragment();
        Bundle args = new Bundle();
        args.putLong(Key.INVITATION_ID, invitationId);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.adapter_post_icon_iv)
    AppCompatImageView invitationPostIconIv;

    @BindView(R.id.adapter_post_name_tv)
    AppCompatTextView invitationDetailNameTv;

    @BindView(R.id.adapter_post_tag_tv)
    AppCompatTextView invitationDetailTagTv;

    @BindView(R.id.adapter_post_content_tv)
    AppCompatTextView invitationDetailContentTv;

    @BindView(R.id.adapter_post_date_tv)
    AppCompatTextView invitationDetailDateTv;

    @BindView(R.id.adapter_post_image_container)
    NineGridView invitationImageContainer;

    @BindView(R.id.adapter_post_reviews_tv)
    AppCompatTextView invitationDetailReviewsTv;

    @BindView(R.id.adapter_post_like_btn)
    LikeButton invitationDetailLikeBtn;

    @BindView(R.id.adapter_post_like_num_tv)
    AppCompatTextView invitationDetailLikeNumTv;

    @BindView(R.id.adapter_post_comment_container)
    LinearLayout invitationCommentContainer;

    @BindView(R.id.invitation_detail_comment_num_tv)
    AppCompatTextView invitationDetailCommentNumTv;

    @BindView(R.id.invitation_detail_sub_iv)
    AppCompatImageView invitationDetailSubIv;


    private long mInvitationId;
    private int mLikeNum = 0;
    private int mDiscussNum = 0;
    private InvitationVO mInvitationVo;
    private PrepareForDiscussionListener mDiscussionDialogListener;


    @Override
    public void onAttach(Context context) {
        try {
            mDiscussionDialogListener = (PrepareForDiscussionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement the PrepareForDiscussionListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        if (savedInstanceState != null) {
            mInvitationVo = savedInstanceState.getParcelable(Key.NEWS);
        } else {
            Bundle args = getArguments();
            mInvitationId = args.getLong(Key.INVITATION_ID, 0L);
        }
        if (mInvitationId == 0L) {
            throw new IllegalArgumentException("InvitationDetailFragment must receive the news's id");
        }
        initToolbar(rootView, getString(R.string.tips_invitation_detail_title), R.mipmap.ic_navigation_back);
        initListener();
    }

    @Override
    protected void onLazyLoad() {
        if (mInvitationVo == null) {
            mPresenter.getInvitationDetailById(mInvitationId);
        } else {
            onGetInvitationDetailInfoSuccess(mInvitationVo);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (mInvitationVo != null) {
            outState.putParcelable(Key.NEWS, mInvitationVo);
        }
        super.onSaveInstanceState(outState);
    }

    @OnClick({R.id.adapter_post_icon_iv, R.id.adapter_post_name_tv,
            R.id.invitation_detail_comment_iv, R.id.invitation_detail_comment_tv, R.id.adapter_post_comment_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.adapter_post_icon_iv:
            case R.id.adapter_post_name_tv:
                if (!isAdded() || mInvitationVo == null || mPresenter == null) return;
                // 跳转用户主页
                startActivity(UserCenterActivity.createIntent(mContext,
                        mPresenter.getModel().getRepositoryHelper().getPreferencesHelper().getCurrentUserId(),
                        mInvitationVo.getUserId()));
                break;
            case R.id.invitation_detail_comment_iv:
            case R.id.invitation_detail_comment_tv:
            case R.id.adapter_post_comment_iv:
                mDiscussionDialogListener.showCommentDialog(getString(R.string.hint_comment), listener);
                // 进行评论
                break;
        }
    }

    @Override
    protected boolean shouldShowNoDataView() {
        return false;
    }

    @Override
    protected int getNoDataStringResId() {
        return 0;
    }

    @Override
    protected void onRetryClick() {
        mPresenter.getInvitationDetailById(mInvitationId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invitation_detail;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }
    // TODO

    @Override
    public void onGetInvitationDetailInfoSuccess(InvitationVO articleVO) {
        this.mInvitationVo = articleVO;
        showInvitationAuthorInfo();
        mPresenter.isFocusOn(articleVO.getUserId());
    }

    private void showInvitationAuthorInfo() {
        GlideUtils.loadUserHead(invitationPostIconIv, mInvitationVo.getHeadUrl());
        invitationDetailNameTv.setText(mInvitationVo.getNickname());
        invitationDetailTagTv.setText(mInvitationVo.getPhysiqueStr());
        invitationDetailDateTv.setText(DateUtils.formatPublishDate(mInvitationVo.getCreateTime()));
        List<ImageInfo> imageInfos = new ArrayList<>();
        String[] images = mInvitationVo.getImages().split(",");
        for (String image : images) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setBigImageUrl(image);
            imageInfo.setThumbnailUrl(image);
            imageInfos.add(imageInfo);
        }
        invitationImageContainer.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfos));
    }


    @Override
    public void onGetAttentionSuccess(boolean isFocusOn) {
        showInvitationSubButton(isFocusOn);
    }

    @Override
    public void operateLikeStateSuccess(boolean isLiked) {
        if (!isAdded()) return;
        invitationDetailLikeBtn.setLiked(isLiked);
        mLikeNum = isLiked ? 1 + mLikeNum : mLikeNum - 1;
        invitationDetailLikeNumTv.setText(getString(R.string.tips_total_like_count_template, mLikeNum));
        showMessage(getString(isLiked ? R.string.tips_like_success : R.string.tips_cancel_like_success));
    }

    @Override
    public void operateLikeStateError() {
        invitationDetailLikeBtn.setLiked(!invitationDetailLikeBtn.isLiked());
        showMessage(getString(R.string.tips_happen_unknown_error));
    }

    @Override
    public void operateAttentionStateSuccess(boolean isFocusOn) {
        showInvitationSubButton(isFocusOn);
        showMessage(getString(isFocusOn ? R.string.tips_focus_on_success : R.string.tips_cancel_focus_on_success));
    }

    @Override
    public void operateArticleCommentStatus(boolean success) {
        mDiscussionDialogListener.dismissCommentDialog();
        showMessage(getString(success ? R.string.tips_comment_success : R.string.tips_comment_failed));
        invitationDetailCommentNumTv.setText(StringUtils.getFormattedOverMaximumString(
                success ? ++mDiscussNum : mDiscussNum, 999, R.string.tips_over_maximum));
    }


    @Override
    public boolean useEventBus() {
        return true;
    }

    private void showInvitationSubButton(boolean isFocusOn) {
        invitationDetailSubIv.setImageResource(isFocusOn ? R.mipmap.ic_focus_on : R.mipmap.ic_not_focus_on);
    }

    private CommentDialog.CommentDialogClickListener listener = new CommentDialog.CommentDialogClickListener() {
        @Override
        public void onDialogBackPressed() {
            mDiscussionDialogListener.dismissCommentDialog();
            mPresenter.getModel().cancelSubscribe();
        }

        @Override
        public void onSendMessage(String content) {
            mPresenter.addComment(mInvitationId, content, -1);
        }
    };

    @Override
    public void dismissDialog() {
        super.dismissDialog();
        if (mDiscussionDialogListener != null) {
            mDiscussionDialogListener.dismissCommentDialog();
        }
    }

    private void initListener() {
        invitationDetailLikeBtn.setLikeButtonClickListener((v, liked) ->
                mPresenter.likeInvitation(mInvitationId));
        invitationDetailSubIv.setOnClickListener(v -> {
            if (mInvitationVo == null)
                return;
            mPresenter.focusOnAuthor(mInvitationVo.getUserId());
        });
    }

}
