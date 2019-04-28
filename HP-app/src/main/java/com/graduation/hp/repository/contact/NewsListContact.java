package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.NewsList;

import java.util.List;

public interface NewsListContact {

    interface View {
        void onDownloadDataSuccess(boolean refresh,List<NewsList> newsLists);
    }

    interface Presenter {
        void downloadInitialData(String category);

        void downloadMoreData(boolean refresh, String category);
    }

}
