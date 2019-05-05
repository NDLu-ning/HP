package com.graduation.hp.repository.contact;

public interface SearchContact {

    interface View {

    }

    interface Presenter {

        void getSearchRecords();

        void saveSearchRecord(String keyword);
    }
}
