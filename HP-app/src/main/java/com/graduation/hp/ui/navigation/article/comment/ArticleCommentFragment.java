package com.graduation.hp.ui.navigation.article.comment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.app.event.DiscussEvent;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.ArticleCommentPresenter;
import com.graduation.hp.repository.contact.ArticleCommentContact;
import com.graduation.hp.repository.http.entity.pojo.ArticleDiscussPO;
import com.graduation.hp.ui.provider.CommentItemAdapter;
import com.graduation.hp.widget.dialog.CommentDialog;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ArticleCommentFragment extends RootFragment<ArticleCommentPresenter>
        implements ArticleCommentContact.View {

    @BindView(R.id.view_main)
    XRecyclerView mRecyclerView;

    @BindView(R.id.adapter_list_subtitle_tv)
    AppCompatTextView adapterListSubtitleTv;

    private CommentItemAdapter mAdapter;
    private long mNewsId;
    private List<ArticleDiscussPO> mList = new ArrayList<>();
    private PrepareForDiscussionListener mDiscussionDialogListener;

    public static ArticleCommentFragment newInstance(long newsId) {
        ArticleCommentFragment fragment = new ArticleCommentFragment();
        Bundle args = new Bundle();
        args.putLong(Key.NEWS_ID, newsId);
        fragment.setArguments(args);
        return fragment;
    }

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
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            mNewsId = bundle.getLong(Key.NEWS_ID, 0L);
        } else {
            mNewsId = savedInstanceState.getLong(Key.NEWS_ID, 0L);
        }
        if (mNewsId == 0L) {
            throw new IllegalArgumentException("ArticleCommentFragment must receive the news's key");
        }
        adapterListSubtitleTv.setText(getString(R.string.tips_all_discussions_title));
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new CommentItemAdapter(getContext(), mList, listener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreEnabled(false);
    }

    @Override
    protected void onLazyLoad() {
        mPresenter.getArticleCommentList(State.STATE_INIT, mNewsId);
    }

    CommentItemAdapter.CommentItemAdapterListener listener = new CommentItemAdapter.CommentItemAdapterListener() {
        @Override
        public void onReplyClick(long articleId, long userId, String nickname, String sourceContent) {
            mDiscussionDialogListener.showCommentDialog(getString(R.string.hint_comment_template, nickname), new CommentDialog.CommentDialogClickListener() {
                @Override
                public void onDialogBackPressed() {
                    mDiscussionDialogListener.dismissCommentDialog();
                    mPresenter.getModel().cancelSubscribe();
                }

                @Override
                public void onSendMessage(String content) {
                    mPresenter.addArticleComment(articleId, content, userId);
                }
            });
        }
    };


    @Override
    protected boolean shouldShowNoDataView() {
        return true;
    }

    @Override
    protected int getNoDataStringResId() {
        return R.string.tips_put_first_comment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_comment;
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
    protected void onEmptyClick() {
        mDiscussionDialogListener.showCommentDialog(getString(R.string.hint_comment), new CommentDialog.CommentDialogClickListener() {
            @Override
            public void onDialogBackPressed() {
                mDiscussionDialogListener.dismissCommentDialog();
                mPresenter.getModel().cancelSubscribe();
            }

            @Override
            public void onSendMessage(String content) {
                mPresenter.addArticleComment(mNewsId, content, -1L);
            }
        });
    }

    @Override
    protected void onRetryClick() {
        mPresenter.getArticleCommentList(State.STATE_INIT, mNewsId);
    }

    @Override
    public void dismissDialog() {
        super.dismissDialog();
        mDiscussionDialogListener.dismissCommentDialog();
    }

    @Override
    public void onDestroyView() {
        if (mRecyclerView != null) {
            mRecyclerView.destroy();
        }
        super.onDestroyView();
    }

    @Override
    public void onGetArticleCommentsSuccess(State state, List<ArticleDiscussPO> list) {
        if (!isAdded()) return;
        if (mPresenter.isRefresh()) {
            mList.clear();
        }
        mRecyclerView.loadMoreComplete();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void operateArticleCommentStatus(boolean success) {
        mDiscussionDialogListener.dismissCommentDialog();
        showMessage(getString(success ? R.string.tips_comment_success : R.string.tips_comment_failed));
        if (success) {
            mPresenter.getArticleCommentList(State.STATE_REFRESH, mNewsId);
        }
        EventBus.getDefault().post(new DiscussEvent(true));
    }
}
