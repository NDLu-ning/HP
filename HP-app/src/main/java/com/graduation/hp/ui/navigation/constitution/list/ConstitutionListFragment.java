package com.graduation.hp.ui.navigation.constitution.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.app.listener.SimpleItemClickListenerAdapter;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.ConstitutionListPresenter;
import com.graduation.hp.repository.contact.ConstitutionListContact;
import com.graduation.hp.repository.http.entity.ArticleVO;
import com.graduation.hp.ui.provider.NewsItemBigProvider;
import com.graduation.hp.ui.provider.NewsItemMultiProvider;
import com.graduation.hp.ui.provider.NewsItemSingleProvider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class ConstitutionListFragment extends RootFragment<ConstitutionListPresenter>
        implements ConstitutionListContact.View {

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Inject
    MultiTypeAdapter mAdapter;

    @Inject
    Items mItems;

    private RefreshLayout mRefreshLayout;
    private int physicalId;
    private int position;

    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        Bundle args = getArguments();
        position = args.getInt(Key.POSITION, 0);
        physicalId = args.getInt(Key.PHYSICAL_ID, 0);
        initMultiTypeAdapter();
    }

    private void initMultiTypeAdapter() {
        mAdapter.register(ArticleVO.class).to(
                new NewsItemSingleProvider(listener),
                new NewsItemMultiProvider(listener),
                new NewsItemBigProvider(listener)
        ).withClassLinker((position, newsList) -> {
            String image = newsList.getImages();
            if (!TextUtils.isEmpty(image)) {
                String[] images = image.split(",");
                if (images.length >= 3) {
                    if (position % 2 == 0) {
                        return NewsItemSingleProvider.class;
                    }
                    return NewsItemMultiProvider.class;
                }
            }
            if (position % 2 == 0) {
                return NewsItemSingleProvider.class;
            } else {
                return NewsItemBigProvider.class;
            }
        });
        mAdapter.setItems(mItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
    }

    private OnItemClickListener listener = new SimpleItemClickListenerAdapter(){

    };


    @Override
    protected void onLazyLoad() {
    }

    @Override
    protected boolean shouldShowNoDataView() {
        return true;
    }

    @Override
    protected int getNoDataStringResId() {
        return R.string.tips_empty_news_list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_constitution_list;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }
}
