package io.tiklab.arbess.setting.hostgroup.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 主机组与主机管理关系实体
 * @author zcamy
 */
@Entity
@Table(name="pip_auth_host_group_details")
public class AuthHostGroupDetailsEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    // 主机组ID
    @Column(name = "group_id")
    private String groupId;

    // 主机ID
    @Column(name = "host_id")
    private String hostId;



    public String getId() {
        return id;
    }



    public AuthHostGroupDetailsEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public AuthHostGroupDetailsEntity setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getHostId() {
        return hostId;
    }

    public AuthHostGroupDetailsEntity setHostId(String hostId) {
        this.hostId = hostId;
        return this;
    }
}
