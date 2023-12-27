package io.thoughtware.matflow.pipeline.definition.model;


import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;




/**
 * @pi.model:io.thoughtware.matflow.pipeline.definition.model.PipelineFollow
 * @desc:流水线收藏模型
 */

@Join
@Mapper
public class PipelineFollow {

    /**
     * @pi.name:id
     * @pi.dataType:string
     * @pi.desc:日志id
     * @pi.value:id24324234343
     */
    private String id;

    /**
     * @pi.name:userId
     * @pi.dataType:string
     * @pi.desc:用户id
     * @pi.value:userId
     */
    private String userId;

    /**
     * @pi.model:pipeline
     * @pi.desc:流水线信息
     */
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
