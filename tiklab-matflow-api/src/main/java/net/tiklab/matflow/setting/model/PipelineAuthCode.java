package net.tiklab.matflow.setting.model;

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
@Mapper(targetAlias = "PipelineAuthCodeEntity")
public class PipelineAuthCode {

    @ApiProperty(name = "codeId",desc="id")
    private String codeId;

    @ApiProperty(name = "type",desc="类型")
    private int type;

    @ApiProperty(name = "authType",desc="源码类型")
    private int authType;

    @ApiProperty(name = "names",desc="服务名称")
    private String name;


    @ApiProperty(name = "username",desc="用户名")
    private String username;

    @ApiProperty(name = "password",desc="密码")
    private String password;

    @ApiProperty(name = "privateKey",desc="token")
    private String privateKey;

    @ApiProperty(name = "createTime")
    private String createTime ;

    @ApiProperty(name = "accessToken",desc="授权信息")
    private String accessToken;

    @ApiProperty(name = "refreshToken",desc="刷新授权")
    private String refreshToken;

    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    @ApiProperty(name = "authPublic",desc="是否公开")
    private int authPublic;

    @Mappings({
            @Mapping(source = "pipelineAuth.authId",target = "authId")
    })
    @JoinQuery(key = "authId")
    private PipelineAuth pipelineAuth;


    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public PipelineAuth getPipelineAuth() {
        return pipelineAuth;
    }

    public void setPipelineAuth(PipelineAuth pipelineAuth) {
        this.pipelineAuth = pipelineAuth;
    }
}


























