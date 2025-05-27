package io.tiklab.arbess.setting.group.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;
import io.tiklab.user.user.model.User;

@Join
@Mapper
public class Group {

    // id
    private String id;

    // 环境名称
    private String groupName;

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

    public Group setId(String id) {
        this.id = id;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public Group setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Group setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Group setUser(User user) {
        this.user = user;
        return this;
    }

    public String getDetail() {
        return detail;
    }

    public Group setDetail(String detail) {
        this.detail = detail;
        return this;
    }
}
