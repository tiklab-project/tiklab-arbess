package io.thoughtware.matflow.pipeline.execute.model;

import io.thoughtware.matflow.pipeline.definition.model.Pipeline;

public class PipelineRunMsg {

    private String pipelineId;


    private String userId;


    private Integer runWay;


    private Pipeline pipeline;

    public PipelineRunMsg() {
    }


    public PipelineRunMsg(String pipelineId, String userId, Integer runWay) {
        this.pipelineId = pipelineId;
        this.userId = userId;
        this.runWay = runWay;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRunWay() {
        return runWay;
    }

    public void setRunWay(Integer runWay) {
        this.runWay = runWay;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }
}
