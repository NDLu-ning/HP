package com.graduation.hp.core.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Ning on 2018/11/20.
 */

public class LogUtils {

    //以下为打印级别，级别从低到高
    public static final int LOG_LEVEL_VERBOSE = 1;
    public static final int LOG_LEVEL_DEBUG = 2;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_WARN = 4;
    public static final int LOG_LEVEL_ERROR = 5;

    private static String APP_NAME;
    private static boolean PRINT_LINE_NUMBER = true;
    private static int LOG_LEVEL = LOG_LEVEL_DEBUG;

    private LogUtils(){
    }

    public static void setAppName(String appName) {
        APP_NAME = appName;
    }

    public static void setPrintLineNumber(boolean printLineNumber) {
        PRINT_LINE_NUMBER = printLineNumber;
    }

    public static void setLogLevel(int logLevel) {
        LOG_LEVEL = logLevel;
    }

    public static void v(String msg){
        if(LOG_LEVEL <= LOG_LEVEL_VERBOSE) {
            String tag = generateTag();
            Log.v(tag, msg);
        }
    }

    public static void d(String msg){
        if(LOG_LEVEL <= LOG_LEVEL_DEBUG) {
            String tag = generateTag();
            Log.d(tag, msg);
        }
    }

    public static void i(String msg){
        if(LOG_LEVEL <= LOG_LEVEL_INFO) {
            String tag = generateTag();
            Log.i(tag, msg);
        }
    }

    public static void w(String msg){
        if(LOG_LEVEL <= LOG_LEVEL_WARN) {
            String tag = generateTag();
            Log.w(tag, msg);
        }
    }

    public static void e(String msg){
        if(LOG_LEVEL <= LOG_LEVEL_ERROR) {
            String tag = generateTag();
            Log.e(tag, msg);
        }
    }


    /**
     * 生成tag
     */
    private static String generateTag() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = "%s.%s";
        if(!TextUtils.isEmpty(APP_NAME)){
            tag = APP_NAME + "__" + tag;
        }
        if(PRINT_LINE_NUMBER){
            tag += "(Line:%d)";
            tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        }else{
            tag = String.format(tag, callerClazzName, caller.getMethodName());
        }
        return tag;
    }
}
