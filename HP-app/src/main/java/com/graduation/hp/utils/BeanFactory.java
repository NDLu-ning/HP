package com.graduation.hp.utils;

import com.graduation.hp.R;
import com.graduation.hp.repository.http.entity.ArticleDiscussPO;
import com.graduation.hp.repository.http.entity.ArticleVO;
import com.graduation.hp.repository.http.entity.CommentItem;
import com.graduation.hp.repository.http.entity.PostItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class BeanFactory {

    private static final String[] titles = {"熬夜伤阳气，易患病，医生：用这3个方法，滋阳补气，寿命长！", "中医学认为，这种水果不仅好吃，还有很高的药用价值！",
            "50岁后，想健康长寿吃什么好？可多吃5种食物，给你的寿命加分",
            "每天早餐一颗鸡蛋，你的胆固醇会升高多少？血脂高的人该怎么吃？", "“豆腐脑”的制作方法，告诉你秘制做法和配方，学会可以摆摊了"};
    private static final String[] images = {"https://p1.pstatp.com/list/dfic-imagehandler/1a38c0a8-00b3-4542-9151-f0c33487acef", "https://p1.pstatp.com/list/dfic-imagehandler/9c2eded8-bd69-4988-bf37-5f465587bb56",
            "https://p1.pstatp.com/list/190x124/pgc-image/4b3ce2709d794a2694a86085e80cac59", "https://p1.pstatp.com/list/dfic-imagehandler/9c2eded8-bd69-4988-bf37-5f465587bb56", "https://p9.pstatp.com/list/190x124/pgc-image/af9c890da6ec4d0782a052b5cefc0714"};
    private static final String[] authors = {"南方健康", "味古的厨房", "天天挺健康", "橘子姐美食", "芒果冰", "泽宇营养师"};

    public static final String[] constitutions = {"平和型", "气虚型", "阳虚型", "阴虚型", "痰湿型", "湿热型", "气郁型", "血淤型", "过敏型"};
    public static final int[] constitutions_color = {R.color.ping_he_color, R.color.qi_xu_color, R.color.yang_xu_color, R.color.yin_xu_color,
            R.color.tan_shi_color, R.color.shi_re_color, R.color.qi_yu_color, R.color.xue_yu_color, R.color.guo_min_color};
    public static final int[] constitutions_bg_res = {R.drawable.healthy_ping_he_tag, R.drawable.healthy_qi_xu_tag, R.drawable.healthy_yang_xu_tag, R.drawable.healthy_yin_xu_tag,
            R.drawable.healthy_tan_shi_tag, R.drawable.healthy_shi_re_tag, R.drawable.healthy_qi_yu_tag, R.drawable.healthy_xue_yu_tag, R.drawable.healthy_guo_min_tag};

    private static final String[] post_titles = {"大厨教你红薯丸子做法，掌握一个技巧，丸子下锅不粘连，一看就会", "它是蔬菜中的补钙王，是菠菜的3倍，清热解毒还降胆固醇"};
    private static final String[] post_author = {"第一美食", "美食达人阿飞", "香哈菜谱"};
    private static final String[] post_author_icon = {"https://p1.pstatp.com/thumb/71a1000e5b3aba8662d3", "https://p3.pstatp.com/thumb/6587000457b0399ea501", "https://p1.pstatp.com/thumb/249b000c0ec891a553a3"};
    private static final String[] post_images = {"http://p1.pstatp.com/large/pgc-image/8b1a1de054124e709b16d8c2c3bca3c5", "http://p3.pstatp.com/large/pgc-image/48d5136ccab147678b2b684edc599442"
            , "http://p3.pstatp.com/large/pgc-image/dbdc355a31c741f4a28eba8e9f27af00", "http://p1.pstatp.com/large/pgc-image/98fee463d6c84f1fa62970454b180e89", "http://p1.pstatp.com/large/pgc-image/edefadbbd9f049b1a4e8732c0b65ef56"
            , "http://p9.pstatp.com/large/pgc-image/8818942829a8429093de035c45bbe312", "http://p9.pstatp.com/large/pgc-image/784f9ed0e8674704846cad82052089a8"};
    private static final String[] names = {"Ken.Dion", "BossCat", "为了她，忘了她!", "等风来", "卖鞋的有志青年", "逐月", "追风筝的人"};
    private static final String[] comments = {"别放糖也挺甜的，我以前做不放油和淀粉，回头试着放点，看口感怎么样？",
            "甜上加甜谁还敢做着吃[捂脸][捂脸]",
            "其实能吃什么？只有胃知道，吃下去舒服就可以了，不必强调一定要吃什么才好，我的胃不能吃面食，吃了就难受，但能吃糯米东西，吃了舒服，所以说各人各胃么，只要胃舒服，爱吃就吃呗。[可爱]",
            "觉得红薯丸子不好吃，有没有同感的",
            "点赞美食达人阿飞！讲解的非常详细！谢谢你！我又学到了一道美食",
            "为什么我炸红薯丸的时候会爆炸，油溅得到处都是",
            "白糖10克，玉米淀粉30克，10克植物油……问题来了:红薯多少？",
            "这样的美食很好，有米面的糯米面的，白面加粉面，也能红薯，也能南瓜，挺好的，喜欢啥样的灵活点，有爱吃糖的多加，不爱吃糖的不加",
            "粘滤面包糠，加奶粉，炼乳，吉士粉味道更好。",
            "木耳菜应该是南方的一种蔬菜，大概四五月份（端午节前）大量上市，叶肥厚，做汤鲜美"};

    public static ArticleVO createNewsList() {
        Random random = new Random();
        int imageSize = random.nextInt(images.length);
        StringBuilder sb = new StringBuilder();
        String image = "";
        if (imageSize > 0) {
            for (int i = 0; i < imageSize; i++) {
                int index = random.nextInt(images.length);
                sb.append(images[index]).append(",");
            }
            image = sb.toString();
            image = image.substring(0, image.length() - 1);
        }
//        ArticlePO newsList = new ArticlePO(random.nextBoolean(), titles[random.nextInt(titles.length)], image, new Date(), authors[random.nextInt(authors.length)],
//                random.nextInt(1000));
        return null;
    }

    public static PostItem createPostItem() {
        Random random = new Random();
        int imageSize = random.nextInt(post_images.length);
        StringBuilder sb = new StringBuilder();
        String image = "";
        if (imageSize > 0) {
            for (int i = 0; i < imageSize; i++) {
                int index = random.nextInt(post_images.length);
                sb.append(post_images[index]).append(",");
            }
            image = sb.toString();
            image = image.substring(0, image.length() - 1);
        }
        int commentCount = random.nextInt(3);
        List<CommentItem> commentList = new ArrayList<>();
        for (int i = 0; i < commentCount; i++) {
            commentList.addAll(createCommentItem());
        }
        int authorIndex = random.nextInt(post_author.length);
        PostItem item = new PostItem(post_author[authorIndex], post_author_icon[authorIndex], random.nextInt(constitutions.length) + "", post_titles[random.nextInt(post_titles.length)],
                image, random.nextInt(100), random.nextInt(100) + 100, new Date(), commentList);
        return item;
    }

    public static PostItem createPost(String author, String authorIcon) {
        Random random = new Random();
        int imageSize = random.nextInt(post_images.length);
        StringBuilder sb = new StringBuilder();
        String image = "";
        if (imageSize > 0) {
            for (int i = 0; i < imageSize; i++) {
                int index = random.nextInt(post_images.length);
                sb.append(post_images[index]).append(",");
            }
            image = sb.toString();
            image = image.substring(0, image.length() - 1);
        }
        int commentCount = random.nextInt(3);
        List<CommentItem> commentList = new ArrayList<>();
        for (int i = 0; i < commentCount; i++) {
            commentList.addAll(createCommentItem());
        }
        PostItem item = new PostItem(author, authorIcon, random.nextInt(constitutions.length) + "", post_titles[random.nextInt(post_titles.length)],
                image, random.nextInt(100), random.nextInt(100) + 100, new Date(), commentList);
        return item;
    }

    public static List<CommentItem> createCommentItem() {
        List<CommentItem> commentList = new ArrayList<>();
        Random random = new Random();
        String commentName = names[random.nextInt(names.length)];
        CommentItem comment = new CommentItem("", commentName, "", "", comments[random.nextInt(comments.length)], new Date());
        commentList.add(comment);
        int replyCount = random.nextInt(3);
        for (int i = 0; i < replyCount; i++) {
            commentList.add(new CommentItem("",
                    names[random.nextInt(names.length)], "", comment.getCommentUserName(), comments[random.nextInt(comments.length)], new Date()));
        }
        return commentList;
    }

    public static List<ArticleDiscussPO> createArticleDisscusPOs(long articleId) {
        List<ArticleDiscussPO> articleDiscussPOS = new ArrayList<>();
        Random random = new Random();
        int index = random.nextInt(names.length);
        String commentName = names[index];
        ArticleDiscussPO discussion = createArticleDiscussPO(random.nextLong() % 1000, articleId, index, commentName, -1L,
                "", comments[random.nextInt(comments.length)]);
        articleDiscussPOS.add(discussion);
        int replyCount = random.nextInt(3);
        for (int i = 0; i < replyCount; i++) {
            int userId = random.nextInt(names.length);
            articleDiscussPOS.add(createArticleDiscussPO(random.nextLong() % 1000, articleId, userId, names[userId], index,
                    commentName, comments[random.nextInt(comments.length)]));
        }
        return articleDiscussPOS;
    }

    private static ArticleDiscussPO createArticleDiscussPO(long id, long articleId, long userId, String userNickname, long replyId, String replyNickname, String context) {
        ArticleDiscussPO articleDiscussPO = new ArticleDiscussPO();
        articleDiscussPO.setDiscussType(replyId == -1 ? 1 : 2);
        articleDiscussPO.setArticleId(articleId);
        articleDiscussPO.setUserId(userId);
        articleDiscussPO.setNickname(userNickname);
        articleDiscussPO.setContext(context);
        articleDiscussPO.setTalkerUserId(replyId);
        articleDiscussPO.setTalkNickname(replyNickname);
        articleDiscussPO.setCreateTime(new Date());
        articleDiscussPO.setId(id);
        return articleDiscussPO;
    }

}
