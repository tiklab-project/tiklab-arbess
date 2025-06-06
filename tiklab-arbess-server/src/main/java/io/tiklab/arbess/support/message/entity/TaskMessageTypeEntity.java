package io.tiklab.arbess.support.message.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_message_type")
public class TaskMessageTypeEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id" ,notNull = true)
    private String id;

    //任务id
    @Column(name = "message_id" ,notNull = true)
    private String messageId;

    //消息类型
    @Column(name = "send_type")
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

    public TaskMessageTypeEntity setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getSendType() {
        return sendType;
    }

    public TaskMessageTypeEntity setSendType(String sendType) {
        this.sendType = sendType;
        return this;
    }
}
