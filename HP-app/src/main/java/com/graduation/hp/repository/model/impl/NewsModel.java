package com.graduation.hp.repository.model.impl;

import com.graduation.hp.core.mvp.BaseModel;
import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.core.repository.http.bean.Result;
import com.graduation.hp.core.utils.RxUtils;
import com.graduation.hp.repository.RepositoryHelper;
import com.graduation.hp.repository.http.entity.NewsList;
import com.graduation.hp.repository.http.service.NewsService;
import com.graduation.hp.repository.model.INewsModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class NewsModel extends BaseModel
        implements INewsModel {

    private RepositoryHelper mRepositoryHelper;

    @Inject
    public NewsModel(RepositoryHelper repositoryHelper) {
        this.mRepositoryHelper = repositoryHelper;
    }

    @Override
    public Single<List<NewsList>> getNewsListByCategory(String category, int page, int limit) {
        HttpHelper httpHelper = mRepositoryHelper.getHttpHelper();
        return httpHelper.obtainRetrofitService(NewsService.class)
                .getNewsListByCategory(category, page, limit)
                .map(RxUtils.mappingResponseToResultList(NewsList.class))
                .map((Function<Result, List<NewsList>>) result -> {
                    return null;
                })
                .compose(RxUtils.rxSchedulerHelper());
    }
}
