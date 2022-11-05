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
@Mapper(targetAlias = "PipelineAuthHostEntity")
public class PipelineAuthHost {

    @ApiProperty(name = "hostId",desc="id")
    private String hostId;

    @ApiProperty(name = "type",desc="类型 1. 普通 2. aliyun 3. 腾讯云" )
    private int type;

    @ApiProperty(name = "authType",desc="认证类型 1.用户名密码 2. 通用认证")
    private int authType;

    @ApiProperty(name = "name",desc="名称")
    private String name;

    @ApiProperty(name = "ip",desc="ip地址")
    private String ip;

    @ApiProperty(name = "port",desc="端口号")
    private int port;

    @ApiProperty(name = "create_time")
    private String createTime;

    @ApiProperty(name = "username",desc="用户名")
    private String username;

    @ApiProperty(name = "password",desc="密码")
    private String password;

    @ApiProperty(name = "privateKey",desc="私钥")
    private String privateKey;

    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    @ApiProperty(name = "authPublic",desc="是否公开")
    private int authPublic;

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
