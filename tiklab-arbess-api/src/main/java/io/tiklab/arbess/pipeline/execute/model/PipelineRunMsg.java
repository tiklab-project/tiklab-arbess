package io.tiklab.arbess.pipeline.execute.model;

import io.tiklab.arbess.support.agent.model.Agent;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;

import javax.validation.groups.Default;

/**
 * 流水线运行消息类
 * 包含流水线运行所需的基本信息
 */
public class PipelineRunMsg {

    // 流水线ID
    private String pipelineId;

    // 用户ID
    private String userId;

    // 运行方式
    private Integer runWay;

    // 流水线信息
    private Pipeline pipeline;

    // 默认执行器
    private String agentId;

    // 执行器信息
    private Agent agent;

    // 实例id
    private String instanceId;

    // 运行状态
    private String runStatus;

    // 触发类型
    private String triggerType = "default";

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

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
