package net.tiklab.matflow.definition.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineMessageUserEntity")
public class PipelineMessageUser {

    @ApiProperty(name="messageId",desc="配置id")
    private String messageId;

    @ApiProperty(name="messageTaskId",desc="类型")
    private String messageTaskId;

    @ApiProperty(name="receiveUser",desc="接收人")
    private String receiveUser;

    @ApiProperty(name="receiveType",desc="接收类型 1.全部 2.仅成功 3.仅失败")
    private String receiveType;

    public String getMessageId() {
        return messageId;
    }

}
