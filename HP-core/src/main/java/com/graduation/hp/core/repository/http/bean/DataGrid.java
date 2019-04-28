package com.graduation.hp.core.repository.http.bean;

import java.util.List;

/**
 * 前端分页数据
 *
 * @author wst
 * @date 2018/12/29 11:24
 **/
public class DataGrid<T> {

    /**
     * list值
     */
    private List<T> rows;

    /**
     * 总数
     */
    private Integer total;

    public DataGrid() {
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
