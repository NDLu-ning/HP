package com.graduation.hp.repository.contact;

public interface NewsDetailContact {

    interface View {
    }

    interface Presenter {

        void getNewsDetailByNewsId(String newsId);

        void addNewsComment(String newsId, String content);


    }
}
