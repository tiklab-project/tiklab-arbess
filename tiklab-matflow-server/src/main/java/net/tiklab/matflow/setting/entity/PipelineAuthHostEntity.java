package net.tiklab.matflow.setting.entity;

import net.tiklab.dal.jpa.annotation.*;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.utils.context.LoginContext;

@Entity
@Table(name="pipeline_auth_host")
public class PipelineAuthHostEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String hostId;

    @Column(name = "name")
    private String names;

    @Column(name = "create_time")
    private String createTime = PipelineUntil.date;
    @Column(name = "type")
    private int authType;

    @Column(name = "ip")
    private String ip;

    @Column(name = "port")
    private int port;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;

    //创建人
    @Column(name = "user_id")
    private String userId = LoginContext.getLoginId();

    //是否公开 true：公开， false：不公开
    @Column(name = "auth_public")
    private int authPublic;


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

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
