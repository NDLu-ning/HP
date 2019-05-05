package com.graduation.hp.utils;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.graduation.hp.R;
import com.graduation.hp.core.HPApplication;

public class StringUtils {


    public static String getFormattedOverMaximumString(int value, int maxValue, @StringRes int templateId) {
        String templateStr = HPApplication.getStringById(templateId);
        if (TextUtils.isEmpty(templateStr) || (!templateStr.contains("%1$s")
                && !templateStr.contains("%s"))) {
            throw new IllegalArgumentException("illegal string template");
        }
        String content;
        if (value >= maxValue) {
            content = maxValue + "+";
        } else {
            content = String.valueOf(value);
        }
        return String.format(templateStr, content);
    }

    public static String getFormattedNumericString(int value) {
        if (value >= 100000000) {
            return HPApplication.getStringById(R.string.a_hundred_million, value / 100000000);
        } else if (value >= 10000) {
            return HPApplication.getStringById(R.string.ten_thousand, value / 10000);
        } else {
            return String.valueOf(value);
        }
    }

}
