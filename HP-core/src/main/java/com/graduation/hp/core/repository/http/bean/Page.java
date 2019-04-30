package com.graduation.hp.core.repository.http.bean;

import java.util.Date;

/**
 * 分页组件
 *
 * @author wst
 * @date 2018/12/26 21:11
 **/
public class Page {

    /**
     * 一页有几个
     */
    private Integer limit;

    /**
     * 第几个开始 （10,20，30）
     */
    private Integer offset;

    /**
     * 开始时间
     */
    private Date createTimeStart;

    /**
     * 结束时间
     */
    private Date createTimeEnd;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}
