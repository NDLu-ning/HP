package com.graduation.hp.repository.model;

import com.graduation.hp.repository.http.entity.PostItem;

import java.util.List;

import io.reactivex.Single;

public interface IPostModel {

    Single<List<PostItem>> getPostListByUserId(long userId, int page, int offset);

}
