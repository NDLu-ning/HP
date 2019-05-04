package com.graduation.hp.ui.navigation.news.comment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.NewsCommentPresenter;
import com.graduation.hp.repository.contact.NewsCommentContact;
import com.graduation.hp.ui.provider.CommentItemAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;

public class NewsCommentFragment extends RootFragment<NewsCommentPresenter>
        implements NewsCommentContact.View {

    @BindView(R.id.view_main)
    XRecyclerView mRecyclerView;

    CommentItemAdapter mAdapter;



    public static NewsCommentFragment newInstance(long newsId) {
        NewsCommentFragment fragment = new NewsCommentFragment();
        Bundle args = new Bundle();
        args.putLong(Key.NEWS_ID, newsId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);

    }

    @Override
    protected void onLazyLoad() {

    }

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
    public void showCommentDialog() {

    }
}
