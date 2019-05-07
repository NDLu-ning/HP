package com.graduation.hp.repository.contact;

import com.graduation.hp.ui.search.SearchFragment;
import com.graduation.hp.ui.search.SearchResultFragment;

public interface SearchContact {

    interface View extends SearchFragment.SearchFragmentListener, SearchResultFragment.SearchResultFragmentListener {


    }

    interface Presenter {

        void getSearchRecords();

        void saveSearchRecord(String keyword);
    }
}
