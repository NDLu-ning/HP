package com.graduation.hp.repository.model.impl;

import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.model.IAttentionModel;

import javax.inject.Inject;

public class AttentionModel extends BaseModel
        implements IAttentionModel {


    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public AttentionModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

}
