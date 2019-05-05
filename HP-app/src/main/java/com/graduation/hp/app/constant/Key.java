package com.graduation.hp.app.constant;

import com.graduation.hp.repository.http.entity.local.ChannelVO;

/**
 *
 */
public class Key {
    public static final ChannelVO[] DEFAULT_CHANNELS = {
            new ChannelVO(1, "推荐"), new ChannelVO(2, "男性健康"),
            new ChannelVO(3, "女性健康"), new ChannelVO(4, "食补"),
            new ChannelVO(5, "心理"), new ChannelVO(6, "老人健康")
    };
    public static final ChannelVO[] CONSTITUTIONS_CHANNELS = {
            new ChannelVO(1, "平和型"), new ChannelVO(2, "气虚型"), new ChannelVO(3, "阳虚型"), new ChannelVO(4, "阴虚型")
            , new ChannelVO(5, "痰湿型"), new ChannelVO(6, "湿热型")
            , new ChannelVO(7, "气郁型"), new ChannelVO(8, "血淤型"), new ChannelVO(9, "过敏型")
    };


    /**
     * 请求参数常量
     */
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AUTHOR_ID = "authorId";
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";
    public static final String TYPE_ID = "typeId";
    public static final String USER_ID_CAMEL_CASE = "userId";
    public static final String ARTICLE_ID = "articleId";
    public static final String CONTEXT = "context";
    public static final String DISCUSS_TYPE = "discuss_type";
    public static final String TALKER_USER_ID = "talker_user_id ";
    public static final String FILE = "file";


    /**
     * 页面传递常量
     */
    public static final String OWNER_ID = "owner_id";
    public static final String USER_ID = "user_id";
    public static final String USER_NICKNAME = "user_nickname";
    public static final String USER_ICON = "user_icon";
    public static final String USER_HEALTHY_NUM = "user_healthy_num";

    public static final String NEWS_ID = "news_id";

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
    public static final String UPDATE_TYPE = "update_type";
    public static final String UPDATE_TITIL_ARRAY = "update_title_array";
    public static final String NEWS = "news";

    public static final String KEYWORD = "keyword";
}
