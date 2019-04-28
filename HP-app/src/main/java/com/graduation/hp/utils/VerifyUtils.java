package com.graduation.hp.utils;

import android.text.TextUtils;

public class VerifyUtils {

    private VerifyUtils() {
    }

    public static boolean isLengthVerified(String value, int min, int max) {
        return !TextUtils.isEmpty(value) && value.length() >= min && value.length() <= max;
    }

    public static boolean isContainsSpecialCharacters(String value) {
        return !TextUtils.isEmpty(value) && (!value.contains("?") || !value.contains(";")
                || value.contains("\\"));
    }

    public static boolean isPhoneVerified(String phone) {
//        return !TextUtils.isEmpty(phone) &&
        return true;
    }

}
