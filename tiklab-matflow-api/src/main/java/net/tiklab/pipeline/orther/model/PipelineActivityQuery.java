package net.tiklab.pipeline.orther.model;

import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import java.util.List;

;

@ApiModel
public class PipelineActivityQuery {

    @ApiProperty(name ="userId",desc = "用户id")
    private String userId;

    @ApiProperty(name ="page",desc = "查询第几页")
    private int page ;

    @ApiProperty(name ="pageSize",desc = "查询数量")
    private int pageSize ;

    @ApiProperty(name ="pageNumber",desc = "页数")
    private int pageNumber ;

    @ApiProperty(name ="dataList",desc = "数据")
    private List<PipelineActivity> dataList ;

    @ApiProperty(name ="listSize",desc = "总数据")
    private int listSize ;

    @ApiProperty(name ="pipelineList",desc = "流水线列表")
    private List<Pipeline> pipelineList;

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

    public List<Pipeline> getPipelineList() {
        return pipelineList;
    }

    public void setPipelineList(List<Pipeline> pipelineList) {
        this.pipelineList = pipelineList;
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<PipelineActivity> getDataList() {
        return dataList;
    }

    public void setDataList(List<PipelineActivity> dataList) {
        this.dataList = dataList;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }
}
