package io.tiklab.arbess.setting.env.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_setting_env")
public class EnvEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    // 环境名称
    @Column(name = "env_name")
    private String envName;

    // 创建时间
    @Column(name = "create_time")
    private String createTime;

    // 用户ID
    @Column(name = "user_id")
    private String userId;

    // 说明
    @Column(name = "detail")
    private String detail;


    public String getId() {
        return id;
    }

    public EnvEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getEnvName() {
        return envName;
    }

    public EnvEntity setEnvName(String envName) {
        this.envName = envName;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public EnvEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public EnvEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public EnvEntity setDetail(String detail) {
        this.detail = detail;
        return this;
    }
}
