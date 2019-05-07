package com.graduation.hp.repository.model;

import com.graduation.hp.repository.http.entity.vo.ArticleVO;

import java.util.List;

import io.reactivex.Single;

public interface INewsModel {


    Single<List<ArticleVO>> getNewsListByUserId(long userId, int page, int limit);

    Single<List<ArticleVO>> getNewsListByTypeId(long userId, int offset, int limit);

    Single<ArticleVO> getNewsById(long newsId);



}
