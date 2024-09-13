package io.thoughtware.arbess.task.code.model;

import io.thoughtware.core.BaseModel;

public class ThirdQuery extends BaseModel {

    private String authId;

    private String houseId;

    // 查询条件
    private String query;

    // 页数
    private Integer page = 1;

    // 每页数量
    private Integer pageNumber = 50;


    public String getHouseId() {
        return houseId;
    }

    public ThirdQuery setHouseId(String houseId) {
        this.houseId = houseId;
        return this;
    }

    public String getAuthId() {
        return authId;
    }

    public ThirdQuery setAuthId(String authId) {
        this.authId = authId;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public ThirdQuery setQuery(String query) {
        this.query = query;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public ThirdQuery setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public ThirdQuery setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }
}
