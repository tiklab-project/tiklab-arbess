package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.doublekit.pipeline.definition.model.Pipeline;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineFollowEntity")
public class PipelineFollow {

    @ApiProperty(name="id",desc="日志id")
    private String id;

    @ApiProperty(name="userId",desc="用户",eg="@selectOne")
    private String userId;

    //流水线
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;


    private String pipelineName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }
}
