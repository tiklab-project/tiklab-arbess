package io.tiklab.arbess.support.message.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_message_user")
public class TaskMessageUserEntity {

    //id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    //类型
    @Column(name = "message_id")
    private String messageId;

    //接收人
    @Column(name = "receive_user")
    private String userId;


    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public TaskMessageUserEntity setId(String id) {
        this.id = id;
        return this;
    }
}
