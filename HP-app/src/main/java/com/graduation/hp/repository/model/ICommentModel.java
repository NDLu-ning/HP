package com.graduation.hp.repository.model;

import com.graduation.hp.repository.http.entity.pojo.ArticleDiscussPO;

import java.util.List;

import io.reactivex.Single;

public interface ICommentModel {

    Single<Boolean> discuss(long articleId, String content, int discussType, long talkerUserId);

    Single<List<ArticleDiscussPO>> getDiscuss(long articleId);
}
