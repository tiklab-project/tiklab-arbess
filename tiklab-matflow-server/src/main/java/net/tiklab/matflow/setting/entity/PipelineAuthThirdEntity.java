package net.tiklab.matflow.setting.entity;

import net.tiklab.dal.jpa.annotation.*;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.utils.context.LoginContext;
@Entity
@Table(name="pipeline_auth_server")
public class PipelineAuthThirdEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String serverId;

    @Column(name = "type")
    private int type;

    @Column(name = "name")
    private String name;

    @Column(name = "auth_type")
    private int authType;

    @Column(name = "create_time")
    private String createTime = PipelineUntil.date;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "server_address")
    private String serverAddress;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    //创建人
    @Column(name = "user_id")
    private String userId = LoginContext.getLoginId();

    //是否公开 true：公开， false：不公开
    @Column(name = "auth_public")
    private int authPublic;

    @Column(name = "message")
    private String message;


    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
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

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
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

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
































