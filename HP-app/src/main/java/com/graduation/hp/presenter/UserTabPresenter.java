package com.graduation.hp.presenter;

import android.text.TextUtils;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.contact.UserTabContact;
import com.graduation.hp.repository.model.impl.UserModel;
import com.graduation.hp.ui.navigation.center.UserTabFragment;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Consumer;

public class UserTabPresenter extends BasePresenter<UserTabFragment, UserModel>
        implements UserTabContact.Presenter {

    @Inject
    public UserTabPresenter(UserModel mMvpModel) {
        super(mMvpModel);
    }

    @Override
    public void getCurrentUserInfo() {
        mMvpModel.addSubscribe(Single.just(Result.ok()).subscribe((Consumer<Object>) o -> {
            RepositoryHelper repositoryHelper = mMvpModel.getRepositoryHelper();
            String icon = repositoryHelper.getPreferencesHelper().getCurrentUserIcon();
            String nickname = repositoryHelper.getPreferencesHelper().getCurrentUserNickname();
            float bmi = repositoryHelper.getPreferencesHelper().getCurrentUserBMI();
            int healthy = repositoryHelper.getPreferencesHelper().getCurrentUserHealthyNum();
            boolean isCurrentUserLogin = !TextUtils.isEmpty(repositoryHelper.getPreferencesHelper().getCurrentUserToken());
            mMvpView.getCurrentUserInfoSuccess(isCurrentUserLogin, icon, nickname, bmi, healthy);
        }));
    }
}
