package com.graduation.hp.core.app.constant;

/**
 * Created by Ning on 2019/3/10.
 */

public class AppSettings {

    private static final String FILE_PROVIDER = "com.graduation.hp.fileprovider";

    private AppSettings() {
    }

    public static String getFileProvider() {
        return FILE_PROVIDER;
    }
}
