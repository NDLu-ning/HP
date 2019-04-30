package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.PostItem;

import java.util.List;

public interface UserPostContact {

    interface View {
        void downloadInitialPostList();

        void onGetPostListSuccess(boolean refresh, List<PostItem> list);
    }

    interface Presenter {
        void downloadInitialPostList(long userId);

        void loadMorePostList(boolean refresh,long userId);
    }
}
