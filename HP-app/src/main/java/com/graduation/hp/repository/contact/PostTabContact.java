package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.PostItem;

import java.util.List;

public interface PostTabContact {

    interface View{
        void onDownloadDataSuccess(boolean refresh, List<PostItem> newsLists);

    }

    interface Presenter{
        boolean checkUserLoginStatus();

        void downloadInitialData();

        void downloadMoreData(boolean refresh);

    }

}
