package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.vo.ArticleVO;

import java.util.List;

public interface UserArticleContact {

    interface View {
        void onGetNewsListSuccess(List<ArticleVO> list);
    }

    interface Presenter {
        void downloadInitialNewsList(long userId);

        void loadMoreNewsList(State state, long userId);
    }
}
