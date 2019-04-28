package com.graduation.hp.repository;


import com.graduation.hp.core.repository.http.HttpHelper;
import com.graduation.hp.repository.preferences.PreferencesHelper;

import javax.inject.Inject;

public class RepositoryHelper {

    PreferencesHelper preferencesHelper;

    HttpHelper httpHelper;

    @Inject
    public RepositoryHelper(PreferencesHelper preferencesHelper, HttpHelper httpHelper) {
        this.preferencesHelper = preferencesHelper;
        this.httpHelper = httpHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    public HttpHelper getHttpHelper() {
        return httpHelper;
    }
}
