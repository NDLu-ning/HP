package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.PostItem;
import com.graduation.hp.repository.http.entity.vo.InvitationVO;

import java.util.List;

public interface UserInvitationContact {

    interface View {
        void onGetDataSuccess(State state, List<InvitationVO> list);

        void operateLikeStateSuccess(boolean isLiked);

        void operateLikeStateError();
    }

    interface Presenter {
        void downloadInitialPostList(long userId);

        void loadMorePostList(State state, long userId);

        void likeInvitation(long id);
    }
}
