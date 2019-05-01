package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.UserCenterContact;
import com.graduation.hp.repository.model.impl.AttentionModel;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.navigation.user.center.UserCenterActivity;

import javax.inject.Inject;

public class UserCenterPresenter extends BasePresenter<UserCenterActivity, UserModel>
        implements UserCenterContact.Presenter {

    @Inject
    AttentionModel attentionModel;

    @Inject
    public UserCenterPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void onGetUserInfo(long id) {
        mMvpModel.addSubscribe(mMvpModel.getUserInfo(id)
                .subscribe(
                        result -> mMvpView.onGetUserSuccess(result),
                        throwable -> handlerApiError(throwable)
                )
        );
    }

    @Override
    public void attentionUser(long authorId) {
        mMvpModel.addSubscribe(attentionModel.getAttentionNumber(authorId)
                .subscribe(
                        result -> {
                        },
                        throwable -> {
                            handlerApiError(throwable);
                        }
                ));
    }
}
