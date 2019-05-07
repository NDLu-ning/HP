package com.graduation.hp.repository.model.impl;

import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.model.IQuestionModel;

import javax.inject.Inject;

public class QuestionModel extends BaseModel
        implements IQuestionModel {

    private final RepositoryHelper mRepositoryHelper;

    @Inject
    public QuestionModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }
}
