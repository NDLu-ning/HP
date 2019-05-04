package com.graduation.hp.repository.contact;

import com.graduation.hp.repository.http.entity.ArticleVO;

public interface NewsDetailContact {

    interface View {
        void onGetNewsDetailInfoSuccess(ArticleVO wrapper);

        void onGetAttentionSuccess(boolean isFocusOn);

    }

    interface Presenter {

        int getLocalTextSize();

        void getNewsDetailByNewsId(long newsId);

        void isFocusOn(long authorId);

        void addComment(long newsId, String content);

        void likeArticle(long mNewsId, boolean liked);

        void focusOnAuthor(long authorId, boolean focusOn);
    }
}
