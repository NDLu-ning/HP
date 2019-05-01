package com.graduation.hp.repository.model;

import com.graduation.hp.repository.http.entity.FocusPO;

import java.util.List;

import io.reactivex.Single;

public interface IAttentionModel {


    Single<Object> focusUser(long authorId);

    Single<Long> getAttentionNumber(long userId);

    Single<List<FocusPO>> getAttentionList(int offset, int limit);


}
