package com.graduation.hp.repository.contact;

import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.ArticleDiscussPO;

import java.util.List;

public interface NewsCommentContact {

    interface View {
        void onGetArticleCommentsSuccess(State state, List<ArticleDiscussPO> list);

        void operateArticleCommentStatus(boolean success);
    }

    interface Presenter {
        void getArticleCommentList(State state, long articleId);

        void addArticleComment(long articleId, String content, long talkerUserId);
    }
}
