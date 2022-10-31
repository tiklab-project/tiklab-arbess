package net.tiklab.matflow.setting.entity;

import net.tiklab.dal.jpa.annotation.*;



@Entity
@Table(name="pipeline_auth")
public class PipelineAuthEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String authId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "auth_type")
    private int authType;

    @Column(name = "url")
    private String url;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "token")
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
