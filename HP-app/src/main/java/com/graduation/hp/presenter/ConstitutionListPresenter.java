package com.graduation.hp.presenter;

import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.ConstitutionListContact;
import com.graduation.hp.repository.model.impl.NewsModel;
import com.graduation.hp.ui.navigation.constitution.list.ConstitutionListFragment;

import javax.inject.Inject;

public class ConstitutionListPresenter extends BasePresenter<ConstitutionListFragment, NewsModel>
        implements ConstitutionListContact.Presenter {

    @Inject
    public ConstitutionListPresenter(NewsModel mMvpModel) {
        super(mMvpModel);
    }
}
