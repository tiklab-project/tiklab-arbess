package io.thoughtware.matflow.task.message.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_message_user")
public class TaskMessageUserEntity {

    //id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "message_id")
    private String messageId;

    //类型
    @Column(name = "task_id")
    private String taskId;

    //接收人
    @Column(name = "receive_user")
    private String userId;

    //接收类型 1.全部 2.仅成功 3.仅失败
    @Column(name = "receive_type")
    private String receiveType;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }
}
