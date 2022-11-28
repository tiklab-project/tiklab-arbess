package net.tiklab.matflow.definition.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineMessageEntity")
public class PipelineMessage {

    @ApiProperty(name="messageId",desc="配置id")
    private String messageId;

    @ApiProperty(name="messageType",desc="消息类型")
    private String messageType;

    //消息类型（site:站内信  sms:短信发送  wechat:微信  mail:邮箱发送）
    private List<String> typeList;

    private int sort;

    private int type;

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }
}
