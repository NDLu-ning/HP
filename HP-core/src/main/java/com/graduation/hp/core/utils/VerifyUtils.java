package com.graduation.hp.core.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class VerifyUtils {

    private VerifyUtils() {
    }

    public static boolean isLengthVerified(String value, int min, int max) {
        return !TextUtils.isEmpty(value) && value.length() >= min && value.length() <= max;
    }

    public static boolean isPhoneVerified(String phoneNumber) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|147)\\d{8}$");
        return pattern.matcher(phoneNumber).matches();
    }

    public static Boolean isContainsSpecialCharacters(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern pattern = Pattern.compile(regEx);
        return !TextUtils.isEmpty(str) && pattern.matcher(str).matches();
    }

}
