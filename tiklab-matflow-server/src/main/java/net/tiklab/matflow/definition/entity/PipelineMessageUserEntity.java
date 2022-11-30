package net.tiklab.matflow.definition.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_message_user")
public class PipelineMessageUserEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "message_id")
    private String messageId;

    //类型
    @Column(name = "message_task_id")
    private String messageTaskId;

    //接收人
    @Column(name = "receive_user")
    private String receiveUser;

    //接收类型 1.全部 2.仅成功 3.仅失败
    @Column(name = "receive_type")
    private String receiveType;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageTaskId() {
        return messageTaskId;
    }

    public void setMessageTaskId(String messageTaskId) {
        this.messageTaskId = messageTaskId;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }
}
