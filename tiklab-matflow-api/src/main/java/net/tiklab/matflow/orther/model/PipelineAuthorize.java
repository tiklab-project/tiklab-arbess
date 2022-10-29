package net.tiklab.matflow.orther.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineAuthorizeEntity")
public class PipelineAuthorize {

    @ApiProperty(name = "id",desc="id")
    private String id;

    @ApiProperty(name = "type",desc="类型 2.gitee 3.github")
    private Integer type;

    @ApiProperty(name = "clientId",desc="授权id")
    private String clientId;

    @ApiProperty(name = "clientSecret",desc="授权密码")
    private String clientSecret;

    @ApiProperty(name = "callbackUrl",desc="回调地址")
    private String callbackUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
