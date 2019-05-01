package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.NewsList;

import java.util.List;

public interface NewsListContact {

    interface View {
        void onDownloadDataSuccess(List<NewsList> newsLists);
    }

    interface Presenter {
        void downloadInitialData(long category);

        void downloadMoreData(State state, long category);
    }

}
