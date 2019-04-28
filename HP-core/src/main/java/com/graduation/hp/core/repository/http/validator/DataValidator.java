package com.graduation.hp.core.repository.http.validator;

import android.text.TextUtils;

import java.util.Map;

/**
 * Created by Ning on 2018/11/27.
 */

public interface DataValidator {

    String validateUrl();

    boolean isRequestParamsValidated(Map<String, Object> params);

    DataValidator DEFAULT = new DataValidator() {

        private final String LOCALHOST = "LOCALHOST";

        @Override
        public String validateUrl() {
            return LOCALHOST;
        }

        @Override
        public boolean isRequestParamsValidated(Map<String, Object> params) {
            return params.containsKey("sign")
                    && TextUtils.isEmpty((String) params.get("sign"));
        }
    };
}
