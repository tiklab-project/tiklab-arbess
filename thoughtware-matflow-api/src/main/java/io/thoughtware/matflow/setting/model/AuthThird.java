package io.thoughtware.matflow.setting.model;


import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;


import io.thoughtware.user.user.model.User;

/**
 * 流水线第三方模型
 */

/**
 * @pi.model:io.thoughtware.matflow.setting.model.AuthThird
 * @desc:
 */
//@ApiModel
@Join
@Mapper
public class AuthThird {

    //@ApiProperty(name = "serverId",desc="id")
    private String serverId;

    //@ApiProperty(name = "type",desc="类型  gitee,github,gitlab,sonar,nexus" )
    private String type;

    //@ApiProperty(name = "authType",desc="认证类型 1.用户名密码 2. 通用认证")
    private int authType;

    //@ApiProperty(name = "name",desc="服务名称")
    private String name;

    //@ApiProperty(name = "createTime")
    private String createTime ;

    //@ApiProperty(name = "username",desc="用户名")
    private String username;

    //@ApiProperty(name = "password",desc="密码")
    private String password;

    //@ApiProperty(name = "privateKey",desc="token")
    private String privateKey;

    //@ApiProperty(name = "serverAddress",desc="服务地址")
    private String serverAddress;

    //@ApiProperty(name = "accessToken",desc="授权信息")
    private String accessToken;

    //@ApiProperty(name = "refreshToken",desc="刷新授权")
    private String refreshToken;

    private String code;

    //@ApiProperty(name = "clientId",desc="授权id")
    private String clientId;

    //@ApiProperty(name = "clientSecret",desc="授权密码")
    private String clientSecret;

    //@ApiProperty(name = "callbackUrl",desc="回调地址")
    private String callbackUrl;

    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    //@ApiProperty(name = "authPublic",desc="是否公开")
    private int authPublic;

    //@ApiProperty(name = "message",desc="授权信息")
    private String message;


    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAuthPublic() {
        return authPublic;
    }

    public void setAuthPublic(int authPublic) {
        this.authPublic = authPublic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}


























