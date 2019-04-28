package com.graduation.hp.utils;

import com.graduation.hp.repository.http.entity.NewsList;

import java.util.Date;
import java.util.Random;

public class BeanFactory {

    private static final String[] titles = {"熬夜伤阳气，易患病，医生：用这3个方法，滋阳补气，寿命长！", "中医学认为，这种水果不仅好吃，还有很高的药用价值！",
            "50岁后，想健康长寿吃什么好？可多吃5种食物，给你的寿命加分",
            "每天早餐一颗鸡蛋，你的胆固醇会升高多少？血脂高的人该怎么吃？", "“豆腐脑”的制作方法，告诉你秘制做法和配方，学会可以摆摊了"};
    private static final String[] images = {"https://p1.pstatp.com/list/dfic-imagehandler/1a38c0a8-00b3-4542-9151-f0c33487acef", "https://p1.pstatp.com/list/dfic-imagehandler/9c2eded8-bd69-4988-bf37-5f465587bb56",
            "https://p1.pstatp.com/list/190x124/pgc-image/4b3ce2709d794a2694a86085e80cac59", "https://p1.pstatp.com/list/dfic-imagehandler/9c2eded8-bd69-4988-bf37-5f465587bb56", "https://p9.pstatp.com/list/190x124/pgc-image/af9c890da6ec4d0782a052b5cefc0714"};
    private static final String[] authors = {"南方健康", "味古的厨房", "天天挺健康", "橘子姐美食", "芒果冰", "泽宇营养师"};


    public static NewsList createNewsList() {
        Random random = new Random();
        int imageSize = random.nextInt(images.length);
        StringBuilder sb = new StringBuilder();
        sb.append(",");
        if (imageSize > 0) {
            int index = random.nextInt(images.length);
            sb.append(images[index]);
        }
        NewsList newsList = new NewsList(random.nextBoolean(), titles[random.nextInt(titles.length)], sb.toString().substring(1), new Date(), authors[random.nextInt(authors.length)],
                random.nextInt(1000));
        return newsList;
    }


}
