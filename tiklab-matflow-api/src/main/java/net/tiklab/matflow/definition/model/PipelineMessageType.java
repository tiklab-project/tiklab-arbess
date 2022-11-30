package net.tiklab.matflow.definition.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineMessageTypeEntity")
public class PipelineMessageType {

    @ApiProperty(name="messageTaskId",desc="配置id")
    private String messageTaskId;

    @ApiProperty(name="type",desc="消息类型")
    private String type;

    //消息类型（site:站内信  sms:短信发送  wechat:微信  mail:邮箱发送）
    private List<String> typeList;


}
