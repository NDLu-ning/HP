package com.graduation.hp.repository.model;

import com.graduation.hp.repository.http.entity.pojo.InvitationDiscussPO;
import com.graduation.hp.repository.http.entity.vo.InvitationVO;

import java.util.List;

import io.reactivex.Single;

public interface IInvitationModel {

    Single<List<InvitationVO>> getInvitationList(int offset, int limit);

    Single<List<InvitationVO>> getInvitationListByPhysiqueId(long physiqueId, int offset, int limit);

    Single<List<InvitationVO>> getInvitationListByUserId(long userId, int offset, int limit);

    Single<InvitationVO> getInvitationById(long invitationId);

}
