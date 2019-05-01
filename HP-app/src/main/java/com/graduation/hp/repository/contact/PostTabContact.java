package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.PostItem;

import java.util.List;

public interface PostTabContact {

    interface View {
        void onDownloadDataSuccess(List<PostItem> newsLists);

    }

    interface Presenter {
        boolean checkUserLoginStatus();

        void downloadInitialData();

        void downloadMoreData(State state);

    }

}
