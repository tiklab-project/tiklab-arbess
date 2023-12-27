package io.thoughtware.matflow.task.message.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;


import io.thoughtware.user.user.model.User;
/**
 * 任务消息接收人模型
 */
//@ApiModel
@Join
@Mapper
public class TaskMessageUser {

    //@ApiProperty(name="messageId",desc="配置id")
    private String messageId;

    //@ApiProperty(name="taskId",desc="类型")
    private String taskId;

    //@ApiProperty(name="user",desc="用户",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    //@ApiProperty(name="receiveType",desc="接收类型 1.全部 2.仅成功 3.仅失败")
    private int receiveType;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }
}
