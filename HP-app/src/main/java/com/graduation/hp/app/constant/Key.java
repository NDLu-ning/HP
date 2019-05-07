package com.graduation.hp.app.constant;

import com.graduation.hp.repository.http.entity.wrapper.ChannelVO;
import com.graduation.hp.repository.http.entity.wrapper.ConstitutionVO;

/**
 *
 */
public class Key {
    public static final ChannelVO[] DEFAULT_CHANNELS = {
            new ChannelVO(1, "推荐"), new ChannelVO(2, "男性健康"),
            new ChannelVO(3, "女性健康"), new ChannelVO(4, "食补"),
            new ChannelVO(5, "心理"), new ChannelVO(6, "老人健康")
    };
    public static final ConstitutionVO[] CONSTITUTIONS_CHANNELS = {
            new ConstitutionVO(1, "阳虚型", "特点是:总是手脚冰冷，不敢吃凉东西；受冷以及吃凉东西容易拉肚子。胃脘部、背部、腰膝部怕冷。性格多沉静、内向。调养方式：多吃甘温益气的食物，比如葱、姜、蒜、花椒、韭菜、辣椒、胡椒等。少食生冷食物如黄瓜、梨、西瓜等。多晒太阳，可选择阳光充足的高楼居住。"),
            new ConstitutionVO(2, "阴虚型", "特点是:经常感到手脚心发热，面颊潮红或偏红，眼睛干涩，皮肤干燥。性情急躁，外向好动。耐冻不耐夏。容易患虚劳、失眠。常便秘或大便干结。调养方式：多吃干凉滋润的食物，如绿豆、冬瓜、芝麻、百合、地瓜；多吃长在水里的东西，如藕、竹笋。中午保持一定的午休时间。避免熬夜、剧烈运动，运动是应该控制出汗量，即时补充水分。"),
            new ConstitutionVO(3, "气虚型", "特点是:说话没劲，容易呼吸短促，容易出虚汗，经常疲乏无力，肌肉松软不实。易患感冒，不耐寒暑，生病抗病能力弱，难痊愈，易患内脏下垂如胃下垂等。调养方式：多吃鱼油益气健脾的食物，如黄豆、白扁豆、香菇、大枣、桂圆、蜂蜜等。以柔缓运动，如散步、太极等为主。"),
            new ConstitutionVO(4, "痰湿型", "特点是:心宽体胖是最大特点，腹部松软肥胖，容易困倦。皮肤出油，多汗且黏，眼睛浮肿，经常感到喉咙有痰，口黏腻或甜，喜爱肥甘甜腻。性格温和、稳重。调养方式：饮食清淡，多食葱蒜、海藻海带、冬瓜萝卜等食物，少食肥肉及甜、黏、油腻食物。可服用化痰祛湿方——二陈汤"),
            new ConstitutionVO(5, "湿热型", "特点是:脸部和鼻尖总是油光发亮，容易生粉刺、疮疖，一开口就能闻到异味。容易心烦急躁。身重困倦，大便粘滞不畅或燥结，小便短黄。饮食清淡，多吃甘寒、甘平的食物，如绿豆、空心菜、芹菜、黄瓜等。少吃辛温助热的食物。戒除烟酒。不要熬夜、过于劳累。适合中长跑、游泳爬山、各种球类等运动。"),
            new ConstitutionVO(6, "血淤型", "特点是:刷牙时牙龈易出血，眼睛常有红丝，皮肤常干燥、粗糙，常常出现疼痛，容易烦躁，健忘，性情急躁。容易出现瘀斑，肤色、口唇黯淡。调养方式：可多食黑豆、海带、紫菜、萝卜、胡萝卜、山楂等具有活血、散结、行气疏肝解郁作用的食物，少食肥肉，并保持睡眠，进行适当的有氧运动。"),
            new ConstitutionVO(7, "特禀型", "特点是:对花粉或某些食物过敏等，常见哮喘、风团、咽喉痒、鼻塞、喷嚏等。皮肤易起抓痕、紫斑。易患哮喘、荨麻疹、花粉症及药物过敏等，对外界适应能力差。调养方式：饮食清淡、均衡，粗细搭配适当，荤素搭配合理。少食荞麦、蚕豆、白扁豆、牛肉、鹅肉、茄子等辛辣之品、腥膻发物及含致敏物质的食物"),
            new ConstitutionVO(8, "气郁型", "特点是:性格内向不稳定，多愁善感、忧郁脆弱，一般比较瘦，经常闷闷不乐，无缘无故地叹气，容易心慌、失眠。调养方式：多吃小麦、葱、蒜、海带、萝卜等行气、解郁、消食醒神的食物。睡前避免饮茶、咖啡等提神醒脑的饮料。气功、太极拳等有氧运动最佳；还应配合心理疏导"),
            new ConstitutionVO(9, "平和型", "特点是:体型匀称健壮，睡眠好，二便正常，平时不容易得病，性格开朗，社会和自然适应能力强。饮食不要过抱、过饥、过冷、过热；多吃五谷杂粮、蔬菜瓜果，少食过于油腻以及辛辣之物，适度运动锻炼。")
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
    public static final String DISCUSS_TYPE = "discussType";
    public static final String TALKER_USER_ID = "talkerUserId ";
    public static final String FILE = "file";
    public static final String TITLE = "title";


    /**
     * 页面传递常量
     */
    public static final String OWNER_ID = "owner_id";
    public static final String USER_ID = "user_id";
    public static final String USER_NICKNAME = "user_nickname";
    public static final String USER_ICON = "user_icon";
    public static final String USER_HEALTHY_NUM = "user_healthy_num";

    public static final String NEWS_ID = "news_id";
    public static final String INVITATION_ID = "invitation_id";
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
    public static final String BOTTOM_SHEET_OPTIONS = "bottom_sheet_options";
}
