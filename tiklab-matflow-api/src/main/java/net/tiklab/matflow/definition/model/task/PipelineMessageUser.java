package net.tiklab.matflow.definition.model.task;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;
import net.tiklab.user.user.model.User;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineMessageUserEntity")
public class PipelineMessageUser {

    @ApiProperty(name="messageId",desc="配置id")
    private String messageId;

    @ApiProperty(name="messageTaskId",desc="类型")
    private String messageTaskId;

    @ApiProperty(name="user",desc="认证配置",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    @ApiProperty(name="receiveType",desc="接收类型 1.全部 2.仅成功 3.仅失败")
    private int receiveType;

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
