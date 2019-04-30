package com.graduation.hp.app.constant;

import com.graduation.hp.repository.http.entity.FavouriteChannel;

public class Key {
    public static final FavouriteChannel[] DEFAULT_CHANNELS = {
            new FavouriteChannel("推荐", ""), new FavouriteChannel("男性健康", ""),
            new FavouriteChannel("女性健康", ""), new FavouriteChannel("食补", ""),
            new FavouriteChannel("心理", ""), new FavouriteChannel("老人健康", "")
    };

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String OWNER_ID = "owner_id";
    public static final String USER_ID = "user_id";
    public static final String USER_NICKNAME = "user_nickname";
    public static final String USER_ICON = "user_icon";
    public static final String USER_HEALTHY_NUM = "user_healthy_num";

    public static final String PAGE = "page";
    public static final String SUB_TAB_ID = "sub_tab_id";
    public static final java.lang.String IS_CURRENT_USER_LOGIN = "is_current_user_login";
    public static final String POSITION = "position";
    public static final String CHANNEL = "channel";
    public static final String USER_BMI = "user_bmi";

    public static final String PHONE_NUMBER = "phone_number";
}
