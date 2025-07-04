package io.tiklab.arbess.setting.hostgroup.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;

import io.tiklab.toolkit.join.annotation.JoinField;
import io.tiklab.user.user.model.User;

import java.util.List;

/**
 * 主机组
 * @author zcamy
 */
@Join
@Mapper
public class AuthHostGroup {

    // ID
    private String groupId;

    // 名称
    private String groupName;

    //创建人
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinField(key = "id")
    private User user;


    //是否公开 1：公开， 2：不公开
    private int authPublic;

    // 说明
    private String details;

    // 创建时间
    private String createTime;


    // 主机组
    private List<AuthHostGroupDetails>  detailsList;

    public List<AuthHostGroupDetails> getDetailsList() {
        return detailsList;
    }

    public AuthHostGroup setDetailsList(List<AuthHostGroupDetails> detailsList) {
        this.detailsList = detailsList;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public AuthHostGroup setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public AuthHostGroup setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public AuthHostGroup setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public User getUser() {
        return user;
    }

    public AuthHostGroup setUser(User user) {
        this.user = user;
        return this;
    }

    public int getAuthPublic() {
        return authPublic;
    }

    public AuthHostGroup setAuthPublic(int authPublic) {
        this.authPublic = authPublic;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public AuthHostGroup setDetails(String details) {
        this.details = details;
        return this;
    }
}
