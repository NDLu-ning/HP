package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.PostItem;

import java.util.List;

public interface UserPostContact {

    interface View {
        void onGetPostListSuccess(List<PostItem> list);
    }

    interface Presenter {
        void downloadInitialPostList(long userId);

        void loadMorePostList(State state, long userId);
    }
}
