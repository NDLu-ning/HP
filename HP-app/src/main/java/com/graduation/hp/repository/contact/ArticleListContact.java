package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.vo.ArticleVO;

import java.util.List;

public interface ArticleListContact {

    interface View {
        void onDownloadDataSuccess(List<ArticleVO> newsLists);
    }

    interface Presenter {
        void downloadInitialData(long category);

        void downloadMoreData(State state, long category);
    }

}
