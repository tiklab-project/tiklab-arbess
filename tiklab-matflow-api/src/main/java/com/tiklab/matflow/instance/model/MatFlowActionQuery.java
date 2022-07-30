package com.tiklab.matflow.instance.model;

;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.postin.annotation.ApiModel;
import com.tiklab.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
public class MatFlowActionQuery {

    @ApiProperty(name ="userId",desc = "用户id")
    private String userId;

    @ApiProperty(name ="page",desc = "查询第几页")
    private int page ;

    @ApiProperty(name ="pageSize",desc = "查询数量")
    private int pageSize ;

    @ApiProperty(name ="pageNumber",desc = "页数")
    private int pageNumber ;

    @ApiProperty(name ="dataList",desc = "数据")
    private List<MatFlowAction> dataList ;

    @ApiProperty(name ="listSize",desc = "总数据")
    private int listSize ;

    @ApiProperty(name ="matFlowList",desc = "流水线列表")
    private List<MatFlow> matFlowList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPage() {
        if (page == 0){
            page=1;
        }
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        if (pageSize == 0){
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {

        this.pageSize = pageSize;
    }

    public List<MatFlow> getMatFlowList() {
        return matFlowList;
    }

    public void setMatFlowList(List<MatFlow> matFlowList) {
        this.matFlowList = matFlowList;
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<MatFlowAction> getDataList() {
        return dataList;
    }

    public void setDataList(List<MatFlowAction> dataList) {
        this.dataList = dataList;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
}
