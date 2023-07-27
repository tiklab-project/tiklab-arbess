package io.tiklab.matflow.setting.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.beans.annotation.Mapping;
import io.tiklab.beans.annotation.Mappings;
import io.tiklab.join.annotation.Join;
import io.tiklab.join.annotation.JoinQuery;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
import io.tiklab.user.user.model.User;


/**
 *  @pi.model: io.tiklab.matflow.setting.model.Auth
 *  desc:流水线基本认证模型
 */

@Join
@Mapper
public class Auth {

    /**
     * @pi.name:authId
     * @pi.dataType:string
     * @pi.desc:id
     * @pi.value:authId
     */
    private String authId;

    /**
     * @pi.name: authType
     * @pi.dataType:int
     * @pi.desc: 类型 1.用户名密码 2.私钥
     * @pi.value: 1
     */
    private int authType;

    /**
     * @pi.name:name
     * @pi.dataType:string
     * @pi.desc:名称
     * @pi.value:name
     */
    private String name;

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
     * @pi.name:createTime
     * @pi.dataType:string
     * @pi.desc:创建时间
     * @pi.value:createTime
     */
    @ApiProperty(name = "createTime")
    private String createTime ;

    /**
     * @pi.model:User
     * @pi.desc:用户信息
     */
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    /**
     * @pi.name:authPublic
     * @pi.dataType:Integer
     * @pi.desc:是否公开1.公开,2.不公开
     * @pi.value:1
     */
    private Integer authPublic;


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
