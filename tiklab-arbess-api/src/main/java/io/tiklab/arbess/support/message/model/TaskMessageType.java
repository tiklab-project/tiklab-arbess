package io.tiklab.arbess.support.message.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 任务消息类型模型
 */
//@ApiModel
@Join
@Mapper
public class TaskMessageType {

    //@ApiProperty(name = "id",desc = "id")
    private String id;

    //@ApiProperty(name = "taskId",desc = "任务id")
    private String messageId;

    //@ApiProperty(name="taskType",desc="消息类型")
    private String sendType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public TaskMessageType setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getSendType() {
        return sendType;
    }

    public TaskMessageType setSendType(String sendType) {
        this.sendType = sendType;
        return this;
    }
}
