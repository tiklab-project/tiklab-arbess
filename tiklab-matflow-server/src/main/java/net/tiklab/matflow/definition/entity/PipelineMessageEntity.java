package net.tiklab.matflow.definition.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_message")
public class PipelineMessageEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "message_id")
    private String messageId;

    //消息类型
    @Column(name = "message_type")
    private String messageType;

    //消息内容
    @Column(name = "date")
    private String date;


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
