package io.tiklab.arbess.task.test.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;


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

    private String testPlanId;

    public String getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(String testPlanId) {
        this.testPlanId = testPlanId;
    }

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
