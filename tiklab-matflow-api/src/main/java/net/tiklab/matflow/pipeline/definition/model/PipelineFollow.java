package net.tiklab.matflow.pipeline.definition.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;


/**
 * 流水线收藏模型
 */

@ApiModel
@Join
@Mapper(targetAlias = "PipelineFollowEntity")
public class PipelineFollow {

    @ApiProperty(name="id",desc="日志id")
    private String id;

    @ApiProperty(name="userId",desc="用户Id",eg="@selectOne")
    private String userId;

    @ApiProperty(name="pipeline",desc="流水线",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.id",target = "pipelineId")
    })
    @JoinQuery(key = "id")
    private Pipeline pipeline;


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



}
