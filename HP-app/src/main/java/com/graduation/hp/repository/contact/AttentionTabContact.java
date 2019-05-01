package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.FocusPO;

import java.util.List;

public interface AttentionTabContact {

    interface View {
        void onDownloadDataSuccess(List<FocusPO> newsLists);
    }

    interface Presenter {

        void downloadMoreData(State state);

        void initialAttentionList();
    }
}
