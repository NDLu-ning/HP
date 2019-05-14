package com.graduation.hp.ui.navigation.constitution.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.listener.OnItemClickListener;
import com.graduation.hp.core.app.listener.SimpleItemClickListenerAdapter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.ConstitutionListPresenter;
import com.graduation.hp.repository.contact.ConstitutionListContact;
import com.graduation.hp.repository.http.entity.vo.InvitationVO;
import com.graduation.hp.repository.http.entity.wrapper.ConstitutionVO;
import com.graduation.hp.ui.navigation.article.detail.ArticleDetailActivity;
import com.graduation.hp.ui.navigation.constitution.detail.InvitationDetailActivity;
import com.graduation.hp.ui.provider.ConstitutionItemBigProvider;
import com.graduation.hp.ui.provider.ConstitutionItemMultiProvider;
import com.graduation.hp.ui.provider.ConstitutionItemSingleProvider;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import retrofit2.http.Body;

public class ConstitutionListFragment extends RootFragment<ConstitutionListPresenter>
        implements ConstitutionListContact.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.view_main)
    RecyclerView mRecyclerView;

    @Inject
    MultiTypeAdapter mAdapter;

    @BindView(R.id.constitution_image)
    AppCompatImageView constitutionImage;

    @BindView(R.id.constitution_title)
    AppCompatTextView constitutionTitle;

    @BindView(R.id.constitution_introduction)
    AppCompatTextView constitutionIntroduction;

    @Inject
    Items mItems;

    private RefreshLayout mRefreshLayout;
    private int mPosition;
    private ConstitutionVO mConstitutionVO;

    public static ConstitutionListFragment newInstance(int position, ConstitutionVO channelVo) {
        ConstitutionListFragment fragment = new ConstitutionListFragment();
        Bundle args = new Bundle();
        args.putInt(Key.POSITION, position);
        args.putParcelable(Key.CHANNEL, channelVo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
        if (savedInstanceState != null) {
            mConstitutionVO = savedInstanceState.getParcelable(Key.CHANNEL);
            mPosition = savedInstanceState.getInt(Key.POSITION);
        } else {
            Bundle args = getArguments();
            mPosition = args.getInt(Key.POSITION, 0);
            mConstitutionVO = args.getParcelable(Key.CHANNEL);
        }
        initView();
        initMultiTypeAdapter();
    }

    private void initView() {
        constitutionImage.setImageResource(Key.CONSTITUTIONS_IMAGE_RES[(int) (mConstitutionVO.getId() - 1)]);
        constitutionTitle.setText(mConstitutionVO.getType());
        constitutionIntroduction.setText(mConstitutionVO.getDetail());
    }

    private void initMultiTypeAdapter() {
        mAdapter.register(InvitationVO.class).to(
                new ConstitutionItemBigProvider(listener),
                new ConstitutionItemMultiProvider(listener),
                new ConstitutionItemSingleProvider(listener)
        ).withClassLinker((position, newsList) -> {
            String image = newsList.getImages();
            if (!TextUtils.isEmpty(image)) {
                String[] images = image.split(",");
                if (images.length >= 3) {
                    if (position % 2 == 0) {
                        return ConstitutionItemSingleProvider.class;
                    }
                    return ConstitutionItemMultiProvider.class;
                }
            }
            if (position % 2 == 0) {
                return ConstitutionItemSingleProvider.class;
            } else {
                return ConstitutionItemBigProvider.class;
            }
        });
        mAdapter.setItems(mItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissDialog() {
        super.dismissDialog();
        if (mRefreshLayout != null && mRefreshLayout.getState() == RefreshState.Refreshing) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout != null && mRefreshLayout.getState() == RefreshState.Loading) {
            mRefreshLayout.finishLoadMore();
        }
    }

    private OnItemClickListener listener = new SimpleItemClickListenerAdapter() {
        @Override
        public void OnItemClick(View view, Object object, int position) {
            InvitationVO invitationVO = (InvitationVO) object;
            startActivity(InvitationDetailActivity.createIntent(getContext(), invitationVO.getId()));
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Key.POSITION, mPosition);
        outState.putParcelable(Key.CHANNEL, mConstitutionVO);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onLazyLoad() {
        mPresenter.getConstitutionInvitationList(State.STATE_INIT, mConstitutionVO.getId());
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
        DaggerFragmentComponent.builder()
                .appComponent(appComponent)
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        if (refreshLayout != null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.getConstitutionInvitationList(State.STATE_MORE, mConstitutionVO.getId());
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        if (!isAdded()) return;
        if (refreshLayout != null) {
            mRefreshLayout = refreshLayout;
        }
        mPresenter.getConstitutionInvitationList(State.STATE_REFRESH, mConstitutionVO.getId());
    }

    @Override
    public void onGetDataSuccess(State state, List<InvitationVO> articleVOList) {
        if (mPresenter.isRefresh()) {
            mItems.clear();
        }
        mItems.addAll(articleVOList);
        mAdapter.notifyDataSetChanged();
    }

}
