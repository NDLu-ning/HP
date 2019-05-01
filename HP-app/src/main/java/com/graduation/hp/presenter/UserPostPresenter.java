package com.graduation.hp.presenter;

import com.graduation.hp.HPApplication;
import com.graduation.hp.R;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.core.repository.http.bean.Page;
import com.graduation.hp.repository.contact.UserPostContact;
import com.graduation.hp.repository.http.entity.PostItem;
import com.graduation.hp.repository.model.impl.PostModel;
import com.graduation.hp.repository.preferences.PreferencesHelper;
import com.graduation.hp.ui.navigation.user.center.UserPostFragment;
import com.graduation.hp.utils.BeanFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;

public class UserPostPresenter extends BasePresenter<UserPostFragment, PostModel>
        implements UserPostContact.Presenter {

    Page page;

    @Inject
    public UserPostPresenter(PostModel mMvpModel) {
        super(mMvpModel);
        page = new Page();
    }


    @Override
    public void downloadInitialPostList(long userId) {
        mMvpView.showLoading();
        loadMorePostList(State.STATE_INIT, userId);
    }

    @Override
    public void loadMorePostList(State state, long userId) {
        setCurState(state);
        if (isRefresh()) {
            page = new Page();
        }
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(
//                mMvpModel.getPostListByUserId(userId, page.getOffset(), page.getLimit())
                Single.create((SingleOnSubscribe<List<PostItem>>) emitter -> {
                    PreferencesHelper preferencesHelper = mMvpModel.getRepositoryHelper().getPreferencesHelper();
                    List<PostItem> postItems = new ArrayList<>();
                    Random random = new Random();
                    int size = random.nextInt(3);
                    for (int i = 0; i < size; i++) {
                        PostItem post = BeanFactory.createPost(
                                preferencesHelper.getCurrentUserNickname(), preferencesHelper.getCurrentUserIcon());
                        postItems.add(post);
                    }
                    emitter.onSuccess(postItems);
                })
                        .doOnSuccess(list -> {
                            if (list != null && list.size() > 0) {
                                page.setOffset(page.getOffset() + page.getLimit());
                            }
                        })
                        .doFinally(() -> mMvpView.dismissDialog())
                        .subscribe(list -> {
                            if (list != null && list.size() > 0) {
                                mMvpView.onGetPostListSuccess(list);
                                mMvpView.showMain();
                            } else {
                                if (isRefresh()) {
                                    mMvpView.showEmpty();
                                } else {
                                    mMvpView.showMessage(HPApplication.getStringById(R.string.tips_load_all_data));
                                }
                            }
                        }, this::handlerApiError));
    }
}
