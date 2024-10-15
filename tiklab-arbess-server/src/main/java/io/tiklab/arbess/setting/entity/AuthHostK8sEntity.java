package io.tiklab.arbess.setting.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_auth_host_k8s")
public class AuthHostK8sEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "host_id")
    private String hostId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "auth_type")
    private int authType;

    @Column(name = "ip")
    private String ip;

    @Column(name = "port")
    private int port;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "private_key")
    private String privateKey;

    //创建人
    @Column(name = "user_id")
    private String userId ;

    //是否公开 1：公开， 2：不公开
    @Column(name = "auth_public")
    private int authPublic;

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAuthPublic() {
        return authPublic;
    }

    public void setAuthPublic(int authPublic) {
        this.authPublic = authPublic;
    }
}
