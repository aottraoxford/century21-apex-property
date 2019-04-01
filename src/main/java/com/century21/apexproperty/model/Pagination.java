package com.century21.apexproperty.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pagination {
    private int page;
    private int limit;
    @JsonIgnore
    private int offset;
    @JsonProperty("total_page")
    private int totalPage;
    @JsonProperty("total_item")
    private int totalItem;

    public Pagination(int page, int limit) {
        setPage(page);
        setLimit(limit);
        setOffset(page * limit - limit);
    }

    public Pagination() {
        page = 1;
        limit = 10;
        offset = 0;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalPage() {

        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        setTotalPage((int) Math.ceil((double) totalItem / limit));
        this.totalItem = totalItem;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", limit=" + limit +
                ", offset=" + offset +
                ", totalPage=" + totalPage +
                ", totalItem=" + totalItem +
                '}';
    }
}
