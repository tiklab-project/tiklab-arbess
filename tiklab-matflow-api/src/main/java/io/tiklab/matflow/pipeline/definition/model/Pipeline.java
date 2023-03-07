package io.tiklab.matflow.pipeline.definition.model;


import io.tiklab.beans.annotation.Mapper;
import io.tiklab.beans.annotation.Mapping;
import io.tiklab.beans.annotation.Mappings;
import io.tiklab.join.annotation.Join;
import io.tiklab.join.annotation.JoinQuery;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
import io.tiklab.privilege.role.model.PatchUser;
import io.tiklab.user.user.model.User;

import java.util.List;

/**
 * 流水线模型
 */

@ApiModel
@Join
@Mapper(targetAlias = "PipelineEntity")
public class Pipeline {

    @ApiProperty(name="id",desc="流水线id")
    private String id;

    @ApiProperty(name="name",desc="流水线名称")
    private String name;

    @ApiProperty(name="user",desc="用户")
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    @ApiProperty(name="createTime",desc="流水线创建时间")
    private String createTime;

    @ApiProperty(name="type",desc="流水线类型 1.多任务 2.多阶段")
    private int type;

    @ApiProperty(name="state",desc="运行状态 1.运行中 2.停止中")
    private int state;

    @ApiProperty(name="power",desc="项目作用域 1.全局 2.项目")
    private int power;

    @ApiProperty(name="color",desc="颜色 1~5随机生成")
    private int color;

    @ApiProperty(name="template",desc="流水线模板")
    private String template;

    @ApiProperty(name="collect",desc="收藏 0.未收藏 1.收藏")
    private int collect;

    @ApiProperty(name="userList",desc="流水线成员")
    private List<PatchUser> userList;

    public Pipeline() {
    }

    public Pipeline(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<PatchUser> getUserList() {
        return userList;
    }

    public void setUserList(List<PatchUser> userList) {
        this.userList = userList;
    }
}
