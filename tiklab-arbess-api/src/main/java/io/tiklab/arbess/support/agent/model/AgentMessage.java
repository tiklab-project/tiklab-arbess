package io.tiklab.arbess.support.agent.model;

/**
 * 代理消息模型
 */
public class AgentMessage {
    /**
     * 消息类型
     */
    private String type;
    /**
     * 消息内容
     */
    private Object message;

    /**
     * 流水线ID
     */
    private String pipelineId;

    /**
     * 租户ID
     */
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
