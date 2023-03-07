package io.tiklab.matflow.task.message.model;

import io.tiklab.postin.annotation.ApiProperty;
import io.tiklab.user.user.model.User;
/**
 * 任务接收人消息类型模型
 */
public class TaskUserSendMessageType {

    @ApiProperty(name="user",desc="接收用户")
    private User user;

    @ApiProperty(name="messageType",desc="类型（1.全部 2.仅成功 3.仅失败）")
    private int messageType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
