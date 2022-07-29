package com.tiklab.matfiow.setting.view.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineTopEntity")
public class PipelineTop {

    @ApiProperty(name="id",desc="id")
    private String id;

    @ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    @ApiProperty(name="viewId",desc="视图id")
    private String viewId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }
}
