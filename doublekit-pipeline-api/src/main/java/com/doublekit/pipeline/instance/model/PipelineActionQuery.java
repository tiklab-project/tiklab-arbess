package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.pipeline.definition.model.Pipeline;

import java.util.List;

@ApiModel
public class PipelineActionQuery {

    @ApiProperty(name ="userId",desc = "用户id")
    private String userId;

    @ApiProperty(name ="page",desc = "查询第几页")
    private int page ;

    @ApiProperty(name ="pageSize",desc = "查询数量")
    private int pageSize ;

    @ApiProperty(name ="userPipeline",desc = "流水线列表")
    List<Pipeline> pipelineList;

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
}
