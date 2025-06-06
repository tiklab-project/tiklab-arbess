package io.tiklab.arbess.support.message.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;


import io.tiklab.user.user.model.User;
/**
 * 任务消息接收人模型
 */
//@ApiModel
@Join
@Mapper
public class TaskMessageUser {

    private String id;

    // 消息id
    private String messageId;

    // 接受人
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    public String getId() {
        return id;
    }

    public TaskMessageUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
