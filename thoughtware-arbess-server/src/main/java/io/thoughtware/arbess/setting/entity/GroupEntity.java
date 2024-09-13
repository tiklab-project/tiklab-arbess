package io.thoughtware.arbess.setting.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name="pip_setting_group")
public class GroupEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;


    // 环境名称
    @Column(name = "group_name")
    private String groupName;

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

    public GroupEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupEntity setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public GroupEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public GroupEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public GroupEntity setDetail(String detail) {
        this.detail = detail;
        return this;
    }
}
