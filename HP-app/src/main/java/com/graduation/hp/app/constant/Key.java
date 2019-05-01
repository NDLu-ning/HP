package com.graduation.hp.app.constant;

import com.graduation.hp.repository.http.entity.local.ChannelVo;

/**
 *
 */
public class Key {
    public static final ChannelVo[] DEFAULT_CHANNELS = {
            new ChannelVo(1, "推荐"), new ChannelVo(2, "男性健康"),
            new ChannelVo(3, "女性健康"), new ChannelVo(4, "食补"),
            new ChannelVo(5, "心理"), new ChannelVo(6, "老人健康")
    };
    public static final ChannelVo[] CONSTITUTIONS_CHANNELS = {
            new ChannelVo(1, "平和型"), new ChannelVo(2, "气虚型"), new ChannelVo(3, "阳虚型"), new ChannelVo(4, "阴虚型")
            , new ChannelVo(5, "痰湿型"), new ChannelVo(6, "湿热型")
            , new ChannelVo(7, "气郁型"), new ChannelVo(8, "血淤型"), new ChannelVo(9, "过敏型")
    };


    /**
     * 请求参数常量
     */
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AUTHOR_ID = "authorId";
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";

    /**
     * 页面传递常量
     */
    public static final String OWNER_ID = "owner_id";
    public static final String USER_ID = "user_id";
    public static final String USER_NICKNAME = "user_nickname";
    public static final String USER_ICON = "user_icon";
    public static final String USER_HEALTHY_NUM = "user_healthy_num";

    public static final String PAGE = "page";
    public static final String SUB_TAB_ID = "sub_tab_id";
    public static final String IS_CURRENT_USER_LOGIN = "is_current_user_login";
    public static final String POSITION = "position";
    public static final String PHYSICAL_ID = "physicalId";
    public static final String CHANNEL = "channel";
    public static final String USER_BMI = "user_bmi";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String ID = "id";
    public static final String USER = "user";
}
