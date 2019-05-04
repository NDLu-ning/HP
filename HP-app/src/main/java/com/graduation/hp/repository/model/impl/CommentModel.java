package com.graduation.hp.repository.model.impl;

import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.contact.ICommentModel;

import javax.inject.Inject;

public class CommentModel extends BaseModel
        implements ICommentModel {

    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public CommentModel(RepositoryHelper repositoryHelper){
        this.mRepositoryHelper = repositoryHelper;
    }



}
