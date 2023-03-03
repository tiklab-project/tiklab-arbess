package net.tiklab.matflow.task.message.model;

import net.tiklab.user.user.model.User;

public class TaskUserSendMessageType {

    //接收用户
    private User user;

    //类型（1.全部 2.仅成功 3.仅失败）
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
