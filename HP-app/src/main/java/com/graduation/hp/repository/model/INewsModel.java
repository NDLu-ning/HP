package com.graduation.hp.repository.model;

import com.graduation.hp.repository.http.entity.NewsList;

import java.util.List;

import io.reactivex.Single;

public interface INewsModel {

    Single<List<NewsList>> getNewsListByCategory(long category, int page, int limit);

}
