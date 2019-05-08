package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.PostItem;
import com.graduation.hp.repository.http.entity.vo.InvitationVO;

import java.util.List;

public interface InvitationTabContact {

    interface View {
        void onDownloadDataSuccess(State state, List<InvitationVO> newsLists);

        void operateLikeStateSuccess(boolean isLiked);

        void operateLikeStateError();
    }

    interface Presenter {
        boolean checkUserLoginStatus();

        long getUserPhysicalId();

        void downloadInitialData();

        void downloadMoreData(State state);

        void likeInvitation(long mNewsId);
    }

}
