package io.thoughtware.arbess.pipeline.execute.model;

import io.thoughtware.arbess.support.agent.model.Agent;
import io.thoughtware.arbess.pipeline.definition.model.Pipeline;

public class PipelineRunMsg {

    private String pipelineId;


    private String userId;


    private Integer runWay;


    private Pipeline pipeline;

    // 默认执行器
    private String agentId;

    private Agent agent;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

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
