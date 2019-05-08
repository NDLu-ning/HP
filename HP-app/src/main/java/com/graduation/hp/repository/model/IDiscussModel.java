package com.graduation.hp.repository.model;

import com.graduation.hp.repository.http.entity.pojo.ArticleDiscussPO;
import com.graduation.hp.repository.http.entity.pojo.InvitationDiscussPO;

import java.util.List;

import io.reactivex.Single;

public interface IDiscussModel {

    Single<Boolean> discuss(long articleId, String content, int discussType, long talkerUserId);

    Single<List<ArticleDiscussPO>> getDiscuss(long articleId);

    Single<Boolean> discussInvitation(long invitationId, String content, int discussType, long talkerUserId);

    Single<List<InvitationDiscussPO>> getInvitationDiscuss(long articleId);

}
