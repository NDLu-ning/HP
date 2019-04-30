package com.graduation.hp.ui.navigation.user.center;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.di.component.DaggerFragmentComponent;
import com.graduation.hp.app.di.module.FragmentModule;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.RootFragment;
import com.graduation.hp.presenter.UserPostPresenter;
import com.graduation.hp.repository.contact.UserPostContact;
import com.graduation.hp.repository.http.entity.PostItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class UserPostFragment extends RootFragment<UserPostPresenter>
        implements UserPostContact.View {


    public static Fragment newInstance(long userId) {
        UserPostFragment fragment = new UserPostFragment();
        Bundle args = new Bundle();
        args.putLong(Key.USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject
    Items mItems;

    @Inject
    MultiTypeAdapter mAdapter;

    @BindView(R.id.view_main)
    RecyclerView mViewHolder;

    @Inject



    @Override
    protected void init(Bundle savedInstanceState, View rootView) {
        super.init(savedInstanceState, rootView);
    }

    @Override
    protected boolean shouldShowNoDataView() {
        return true;
    }

    @Override
    protected int getNoDataStringResId() {
        return R.string.tips_empty_post_list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_info;
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
    public void downloadInitialPostList() {
        mPresenter.downloadInitialPostList();
    }

    @Override
    public void onGetPostListSuccess(boolean refresh, List<PostItem> list) {
        if(refresh){

        }
    }
}
