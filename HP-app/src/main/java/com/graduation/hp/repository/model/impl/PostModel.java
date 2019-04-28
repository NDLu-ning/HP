package com.graduation.hp.repository.model.impl;

import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.model.IPostModel;

import javax.inject.Inject;

public class PostModel extends BaseModel
        implements IPostModel {


    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public PostModel(RepositoryHelper repositoryHelper){
        this.mRepositoryHelper = repositoryHelper;
    }
}
