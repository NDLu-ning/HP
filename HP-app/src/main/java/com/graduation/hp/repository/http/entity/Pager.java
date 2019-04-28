package com.graduation.hp.repository.http.entity;

public class Pager {

    private int page;
    private int count;

    public Pager() {
    }

    public Pager(int page, int count) {
        this.page = page;
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

