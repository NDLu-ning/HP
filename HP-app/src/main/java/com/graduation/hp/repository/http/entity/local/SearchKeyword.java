package com.graduation.hp.repository.http.entity.local;


import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.Date;

/**
 * 本地搜索记录
 */
public class SearchKeyword implements Comparable<SearchKeyword> {

    private int queryCount;
    private String keyword;
    private Date createTime;

    public SearchKeyword() {
    }

    public SearchKeyword(String keyword) {
        this.queryCount = 1;
        this.keyword = keyword;
        this.createTime = new Date();
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SearchKeyword)) {
            return false;
        }
        SearchKeyword searchKeyword = (SearchKeyword) obj;
        return keyword.equals(searchKeyword.keyword);
    }

    @Override
    public int compareTo(@NonNull SearchKeyword searchKeyword) {
        if (searchKeyword != null) {
            return getQueryCount() - searchKeyword.getQueryCount();
        }
        return 0;
    }
}
