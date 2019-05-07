package com.graduation.hp.repository.http.entity.pojo;

import com.graduation.hp.core.repository.http.bean.Page;

import java.util.Date;

/**
 * t_focus表对应实体
 *
 * @author shengting_wang
 */
public class FocusPO extends Page {

    /**
     *
     */
    private Long id;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 作者主键
     */
    private Long authorId;

    /**
     * 关注状态（1已关注，2取消关注，3重新关注）
     */
    private Integer status;

    /**
     * 用户的文章数
     */
    private Integer articleNum;

    /**
     * 用户描述
     */
    private String remark;

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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(Integer articleNum) {
        this.articleNum = articleNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
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

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String headUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


}