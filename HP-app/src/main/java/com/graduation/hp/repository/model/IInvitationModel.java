package com.graduation.hp.repository.model;

import com.graduation.hp.repository.http.entity.vo.InvitationVO;

import java.util.List;

import io.reactivex.Single;

public interface IInvitationModel {


    Single<List<InvitationVO>> getInvitationListByTypeId(long typeId, int offset, int limit);

    Single<List<InvitationVO>> getInvitationListByUserId(long userId, int offset, int limit);

}
