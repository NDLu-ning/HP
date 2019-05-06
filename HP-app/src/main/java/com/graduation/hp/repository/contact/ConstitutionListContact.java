package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.InvitationVO;

import java.util.List;

public interface ConstitutionListContact {

    interface View {

        void onGetDataSuccess(State state, List<InvitationVO> articleVOList);
    }

    interface Presenter {

        void getConstitutionNewsList(State state, long typeId);
    }


}
