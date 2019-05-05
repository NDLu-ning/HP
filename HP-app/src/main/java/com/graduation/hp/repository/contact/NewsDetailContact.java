package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.ArticleVO;

public interface NewsDetailContact {

    interface View {
        void onGetNewsDetailInfoSuccess(ArticleVO wrapper);

        void onGetAttentionSuccess(boolean isFocusOn);

        void operateLikeStateSuccess(boolean isLiked);

        void operateAttentionStateSuccess(boolean isFocusOn);

        void operateArticleCommentStatus(boolean success);

        void operateLikeStateError();
    }

    interface Presenter {

        int getLocalTextSize();

        void getNewsDetailByNewsId(long newsId);

        void isFocusOn(long authorId);

        void addComment(long newsId, String content);

        void likeArticle(long newsId);

        void focusOnAuthor(long authorId);
    }
}
