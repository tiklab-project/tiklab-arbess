package io.tiklab.arbess.setting.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * @author zcamy
 */

@Entity
@Table(name="pip_auth_host_group")
public class AuthHostGroupEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String groupId;

    @Column(name = "group_name")
    private String groupName;

    //创建人
    @Column(name = "user_id")
    private String userId ;

    //是否公开 1：公开， 2：不公开
    @Column(name = "auth_public")
    private int authPublic;

    @Column(name = "details")
    private String details;

    @Column(name = "create_time")
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public AuthHostGroupEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public AuthHostGroupEntity setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public AuthHostGroupEntity setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public AuthHostGroupEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public int getAuthPublic() {
        return authPublic;
    }

    public AuthHostGroupEntity setAuthPublic(int authPublic) {
        this.authPublic = authPublic;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public AuthHostGroupEntity setDetails(String details) {
        this.details = details;
        return this;
    }
}
