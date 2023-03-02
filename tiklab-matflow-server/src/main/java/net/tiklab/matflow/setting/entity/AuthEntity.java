package net.tiklab.matflow.setting.entity;

import net.tiklab.dal.jpa.annotation.*;

/**
 * 流水线基本认证实体
 */
@Entity
@Table(name="pip_auth")
public class AuthEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String authId;

    @Column(name = "name")
    private String name;

    //认证类型（1.用户名密码 2.私钥）
    @Column(name = "auth_type")
    private int authType;

    @Column(name = "create_time")
    private String createTime ;

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

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
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
