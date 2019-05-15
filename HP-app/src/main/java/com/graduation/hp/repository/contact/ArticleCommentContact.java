package com.graduation.hp.repository.contact;

import com.graduation.hp.app.event.DiscussEvent;
import com.graduation.hp.core.mvp.State;
import com.graduation.hp.repository.http.entity.pojo.ArticleDiscussPO;

import java.util.List;

public interface ArticleCommentContact {

    interface View {
        void onGetArticleCommentsSuccess(State state, List<ArticleDiscussPO> list);

        void operateArticleCommentStatus(DiscussEvent event);
    }

    interface Presenter {
        void getArticleCommentList(State state, long articleId);

        void addArticleComment(long articleId, String content, long talkerUserId);
    }
}
