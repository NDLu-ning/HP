package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.AttentionTabContact;
import com.graduation.hp.repository.model.impl.AttentionModel;
import com.graduation.hp.ui.navigation.attention.AttentionTabFragment;

import javax.inject.Inject;

public class AttentionTabPresenter extends BasePresenter<AttentionTabFragment, AttentionModel>
        implements AttentionTabContact {



    @Inject
    public AttentionTabPresenter(AttentionModel mMvpModel) {
        super(mMvpModel);
    }



}
