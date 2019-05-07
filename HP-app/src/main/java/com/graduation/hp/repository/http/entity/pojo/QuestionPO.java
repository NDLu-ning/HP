package com.graduation.hp.repository.http.entity.pojo;

import java.util.Date;

/**
 * t_question表对应实体
 *
 * @author shengting_wang
 * @date 2019-04-30
 */
public class QuestionPO {
    /**
     * 问题的主键
     */
    private Long id;

    /**
     * 内容
     */
    private String context;

    /**
     * 体质主键
     */
    private Long physiqueId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Long getPhysiqueId() {
        return physiqueId;
    }

    public void setPhysiqueId(Long physiqueId) {
        this.physiqueId = physiqueId;
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