package net.tiklab.matflow.task.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineMessageTypeEntity")
public class PipelineMessageType {

    @ApiProperty(name="messageTaskId",desc="配置id")
    private String messageTaskId;

    @ApiProperty(name="taskType",desc="消息类型")
    private String taskType;

    @ApiProperty(name="configId",desc="配置id")
    private String configId;

    @ApiProperty(name="name",desc="名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageTaskId() {
        return messageTaskId;
    }

    public void setMessageTaskId(String messageTaskId) {
        this.messageTaskId = messageTaskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}
