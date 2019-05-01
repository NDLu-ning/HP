package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.NewsList;

import java.util.List;

public interface UserNewsContact {

    interface View {
        void onGetNewsListSuccess(List<NewsList> list);
    }

    interface Presenter {
        void downloadInitialNewsList(long userId);

        void loadMoreNewsList(State state, long userId);
    }
}
