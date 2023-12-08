package io.thoughtware.matflow.setting.model;

import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.beans.annotation.Mapping;
import io.thoughtware.beans.annotation.Mappings;
import io.thoughtware.join.annotation.Join;
import io.thoughtware.join.annotation.JoinQuery;

/**
 * 主机组与主机的关联关系
 * @author zcamy
 */
@Join
@Mapper
public class AuthHostGroupDetails {


    private String id;

    // 主机组ID
    private String groupId;

    // 主机ID
    @Mappings({
            @Mapping(source = "authHost.hostId",target = "hostId")
    })
    @JoinQuery(key = "hostId")
    private AuthHost authHost;


    public String getId() {
        return id;
    }

    public AuthHostGroupDetails setId(String id) {
        this.id = id;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public AuthHostGroupDetails setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public AuthHost getAuthHost() {
        return authHost;
    }

    public AuthHostGroupDetails setAuthHost(AuthHost authHost) {
        this.authHost = authHost;
        return this;
    }
}
