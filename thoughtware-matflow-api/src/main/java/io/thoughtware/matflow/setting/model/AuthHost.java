package io.thoughtware.matflow.setting.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;


import io.thoughtware.user.user.model.User;

/**
 * @pi.model: io.thoughtware.matflow.setting.model.AuthHost
 * desc:流水线主机认证模型
 */
@Join
@Mapper
public class AuthHost {

    /**
     * @pi.name:hostId
     * @pi.dataType:string
     * @pi.desc:id
     * @pi.value:hostId
     */
    private String hostId;

    /**
     * @pi.name:type
     * @pi.dataType:string
     * @pi.desc:类型 common 普通  aliyun  阿里云； tencent. 腾讯云
     * @pi.value:aliyun
     */
    private String type;

    /**
     * @pi.name:authType
     * @pi.dataType:Integer
     * @pi.desc:认证类型 1.用户名密码 2. 通用认证
     * @pi.value: 1
     */
    private Integer authType;

    /**
     * @pi.name:name
     * @pi.dataType:string
     * @pi.desc:名称
     * @pi.value:name
     */
    private String name;

    /**
     * @pi.name:ip
     * @pi.dataType:string
     * @pi.desc:ip地址
     * @pi.value:ip
     */
    private String ip;

    /**
     * @pi.name:port
     * @pi.dataType:Integer
     * @pi.desc:端口号
     * @pi.value:8080
     */
    private Integer port;

    /**
     * @pi.name:createTime
     * @pi.dataType:string
     * @pi.desc:创建时间
     * @pi.value:createTime
     */
    private String createTime;

    /**
     * @pi.name:username
     * @pi.dataType:string
     * @pi.desc:用户名
     * @pi.value:username
     */
    private String username;

    /**
     * @pi.name:password
     * @pi.dataType:string
     * @pi.desc:密码
     * @pi.value:password
     */
    private String password;

    /**
     * @pi.name:privateKey
     * @pi.dataType:string
     * @pi.desc:私钥
     * @pi.value:privateKey
     */
    private String privateKey;

    /**
     * @pi.model:user
     * @pi.desc:创建人
     */
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    /**
     * @pi.name:authPublic
     * @pi.dataType:Integer
     * @pi.desc:是否公开 1.公开 3.不公开
     * @pi.value:1
     */
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
