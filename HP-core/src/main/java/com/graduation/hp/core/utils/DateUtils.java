package com.graduation.hp.core.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private DateUtils() {
    }

    public static String formatPublishDate(Date publishDate) {
        long current = System.currentTimeMillis() / 1000;
        long publish = publishDate.getTime() / 1000;
        long result = current - publish;
        if (result >= 14400) {
            return DateUtils.formatDate("MM-dd", publishDate);
        } else if (result >= 3600) {
            return result / 3600 + "小时前";
        } else if (result >= 60) {
            return result / 60 + "分钟前";
        } else {
            return "刚刚";
        }
    }

    public static String formatDate(String fmt, Date date) {
        fmt = !TextUtils.isEmpty(fmt) ? fmt : "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(fmt, Locale.CHINA);
        return format.format(date);
    }

}
