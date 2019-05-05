package com.graduation.hp.repository.model;

import io.reactivex.Single;

public interface ILikeModel {


    Single<Boolean> like(long articleId);

}
