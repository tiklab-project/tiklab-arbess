package net.tiklab.matflow.orther.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineThirdAddressEntity")
public class PipelineThirdAddress {

    @ApiProperty(name = "authorizedId",desc="id")
    private String authorizedId;

    @ApiProperty(name = "authType",desc="类型 1.gitee 2.github")
    private Integer authType;

    @ApiProperty(name = "clientId",desc="授权id")
    private String clientId;

    @ApiProperty(name = "clientSecret",desc="授权密码")
    private String clientSecret;

    @ApiProperty(name = "callbackUrl",desc="回调地址")
    private String callbackUrl;

    public String getAuthorizedId() {
        return authorizedId;
    }

    public void setAuthorizedId(String authorizedId) {
        this.authorizedId = authorizedId;
    }


    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
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
