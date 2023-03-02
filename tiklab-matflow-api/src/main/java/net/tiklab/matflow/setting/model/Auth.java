package net.tiklab.matflow.setting.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;
import net.tiklab.user.user.model.User;

/**
 * 流水线基本认证模型
 */

@ApiModel
@Join
@Mapper(targetAlias = "AuthEntity")
public class Auth {

    @ApiProperty(name = "authId",desc="id")
    private String authId;

    @ApiProperty(name = "authType",desc="类型 1.用户名密码 2.私钥")
    private int authType;

    @ApiProperty(name = "name",desc="名称")
    private String name;

    @ApiProperty(name = "username",desc="用户名")
    private String username;

    @ApiProperty(name = "password",desc="密码")
    private String password;

    @ApiProperty(name = "privateKey",desc="私钥")
    private String privateKey;

    @ApiProperty(name = "createTime")
    private String createTime ;

    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    @ApiProperty(name = "authPublic",desc="是否公开1.公开,2.不公开")
    private int authPublic;


    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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
}
