package com.doublekit.pipeline.definition.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.dal.jpa.criteria.annotation.CriteriaQuery;
import com.doublekit.dal.jpa.criteria.annotation.QueryField;
import com.doublekit.dal.jpa.criteria.annotation.QueryTypeEnum;

@ApiModel
@CriteriaQuery(entityAlias = "PipelineEntity")
public class PipelineQuery {
    //流水线名称
    @ApiProperty(name="pipelineName",desc="流水线名称",required = true)
    @QueryField(type = QueryTypeEnum.like)
    private String pipelineName;

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }
}
