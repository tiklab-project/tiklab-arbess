package net.tiklab.matflow.setting.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineAuthEntity")
public class PipelineAuth {

    @ApiProperty(name = "authId",desc = "id")
    private String authId;

    @ApiProperty(name = "name",desc = "名称")
    private String name;

    @ApiProperty(name = "type",desc = "类型")
    private int type;

    @ApiProperty(name = "authType",desc = "授权类型")
    private int authType;

    @ApiProperty(name = "url",desc = "地址")
    private String url;

    @ApiProperty(name = "createTime",desc = "创建时间")
    private String createTime ;

    @ApiProperty(name = "username",desc = "用户名")
    private String username;

    @ApiProperty(name = "password",desc = "密码")
    private String password;

    @ApiProperty(name = "token",desc = "凭证")
    private String token;


    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
