package com.graduation.hp.repository.http.entity;

import java.util.Date;

/**
 * t_answer表对应实体
 * 
 * @author shengting_wang
 */
public class AnswerPO {

    /**
     * 答案主键
     */
    private Long id;

    /**
     * 答案内容
     */
    private String answerContext;

    /**
     * 答案分数
     */
    private Long score;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerContext() {
        return answerContext;
    }

    public void setAnswerContext(String answerContext) {
        this.answerContext = answerContext;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
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
}