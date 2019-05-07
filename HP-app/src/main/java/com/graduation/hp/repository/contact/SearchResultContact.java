package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.vo.ArticleVO;

import java.util.List;

public interface SearchResultContact {

    interface View {
        void onSearchKeywordSuccess(State state, List<ArticleVO> articleVOS);
    }

    interface Presenter {
        void searchKeywordNewsList(State state, String keyword);
    }
}
