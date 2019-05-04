package com.graduation.hp.repository.model.impl;

import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.model.ISearchModel;

import javax.inject.Inject;

public class SearchModel extends BaseModel
        implements ISearchModel {

    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public SearchModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

}
