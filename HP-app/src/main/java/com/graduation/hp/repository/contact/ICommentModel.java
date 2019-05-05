package com.graduation.hp.repository.contact;

import io.reactivex.Single;

public interface ICommentModel {

    Single<Boolean> discuss(long articleId, String content, int discussType, long talkerUserId);

}
