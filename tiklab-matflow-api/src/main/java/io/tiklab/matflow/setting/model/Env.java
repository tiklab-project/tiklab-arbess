package io.tiklab.matflow.setting.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.beans.annotation.Mapping;
import io.tiklab.beans.annotation.Mappings;
import io.tiklab.join.annotation.Join;
import io.tiklab.join.annotation.JoinQuery;
import io.tiklab.user.user.model.User;

import java.util.function.Predicate;

@Join
@Mapper
public class Env {

    // id
    private String id;

    // 环境名称
    private String envName;

    // 创建时间
    private String createTime;

    /**
     * @pi.model:User
     * @pi.desc:用户
     */
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    // 说明
    private String detail;

    public String getId() {
        return id;
    }

    public Env setId(String id) {
        this.id = id;
        return this;
    }

    public String getEnvName() {
        return envName;
    }

    public Env setEnvName(String envName) {
        this.envName = envName;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Env setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Env setUser(User user) {
        this.user = user;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public Env setDetail(String detail) {
        this.detail = detail;
        return this;
    }
}
