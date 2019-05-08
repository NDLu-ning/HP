package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.pojo.InvitationDiscussPO;
import com.graduation.hp.repository.http.entity.vo.InvitationVO;

import java.util.List;

public interface InvitationDetailContact {

    interface Presenter {

        void focusOnAuthor(long userId);

        void likeInvitation(long invitationId);

        void addComment(long invitationId, String content, long talkerUserId);

        void isFocusOn(long userId);

        void getInvitationDetail(long invitationId);

        void getInvitationDiscuss(long invitationId);
    }

    interface View {

        void onGetInvitationDetailInfoSuccess(InvitationVO articleVO);

        void onGetAttentionSuccess(boolean isFocusOn);

        void onGetInvitationDiscussSuccess(List<InvitationDiscussPO> invitationDiscussPOList);

        void operateLikeStateSuccess(boolean isLiked);

        void operateLikeStateError();

        void operateAttentionStateSuccess(boolean isFocusOn);

        void operateArticleCommentStatus(boolean success);

        void onGetInvitationDiscussEmpty();
    }
}
