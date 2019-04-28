package com.graduation.hp.repository.model.impl;

import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.model.ICollectModel;

import javax.inject.Inject;

public class CollectModel extends BaseModel
        implements ICollectModel {

    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public CollectModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

}
