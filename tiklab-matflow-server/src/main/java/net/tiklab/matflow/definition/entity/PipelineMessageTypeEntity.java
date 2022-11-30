package net.tiklab.matflow.definition.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_message_type")
public class PipelineMessageTypeEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "message_task_id")
    private String messageTaskId;

    //消息类型
    @Column(name = "type")
    private String type;

    public String getMessageTaskId() {
        return messageTaskId;
    }

    public void setMessageTaskId(String messageTaskId) {
        this.messageTaskId = messageTaskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
