package com.graduation.hp.repository.model;

import com.graduation.hp.repository.http.entity.vo.ArticleVO;

import java.util.List;

import io.reactivex.Single;

public interface IArticleModel {


    Single<List<ArticleVO>> getArticleListByUserId(long userId, int page, int limit);

    Single<List<ArticleVO>> getArticleListByTypeId(long typeId, int offset, int limit);

    Single<ArticleVO> getNewsById(long newsId);



}
