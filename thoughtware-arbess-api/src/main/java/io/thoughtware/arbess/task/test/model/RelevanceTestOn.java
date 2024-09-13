package io.thoughtware.arbess.task.test.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;
import io.thoughtware.arbess.pipeline.definition.model.Pipeline;


//@ApiModel
@Join
@Mapper
public class RelevanceTestOn {


    private String relevanceId;

    @Mappings({
            @Mapping(source = "pipeline.id",target = "pipelineId")
    })
    @JoinQuery(key = "id")
    private Pipeline pipeline;

    private String time;

    private String authId;

    private String createTime;

    private String testonId;

    private Integer status;

    private Object object;

    private String url;

    public String getAuthId() {
        return authId;
    }

    public RelevanceTestOn setAuthId(String authId) {
        this.authId = authId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String getTestonId() {
        return testonId;
    }

    public void setTestonId(String testonId) {
        this.testonId = testonId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
