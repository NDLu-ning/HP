package com.graduation.hp.repository.http.entity;

import java.util.Date;

/**
 * t_article_discuss表对应实体
 * 
 * @author shengting_wang
 */
public class ArticleDiscussPO {

    /**
     * 评论主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 类型（1：评论 2：对话）
     */
    private Integer discussType;

    /**
     * 交谈者用户id
     */
    private Long talkerUserId;

    /**
     * 评论内容
     */
    private String context;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 交谈者用户昵称
     */
    private String talkNickname;

    public ArticleDiscussPO() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Integer getDiscussType() {
        return discussType;
    }

    public void setDiscussType(Integer discussType) {
        this.discussType = discussType;
    }

    public Long getTalkerUserId() {
        return talkerUserId;
    }

    public void setTalkerUserId(Long talkerUserId) {
        this.talkerUserId = talkerUserId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTalkNickname() {
        return talkNickname;
    }

    public void setTalkNickname(String talkNickname) {
        this.talkNickname = talkNickname;
    }
}