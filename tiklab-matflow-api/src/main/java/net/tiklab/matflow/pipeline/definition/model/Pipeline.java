package net.tiklab.matflow.pipeline.definition.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;
import net.tiklab.privilege.role.model.PatchUser;
import net.tiklab.user.user.model.User;

import java.util.List;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineEntity")
public class Pipeline {

    //流水线id
    @ApiProperty(name="id",desc="流水线id")
    private String id;

    //流水线名称
    @ApiProperty(name="name",desc="流水线名称")
    private String name;

    //流水线创建人
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    //流水线创建时间
    @ApiProperty(name="createTime",desc="流水线创建时间")
    private String createTime;

    //流水线类型
    @ApiProperty(name="type",desc="流水线类型")
    private int type;

    //运行状态
    @ApiProperty(name="state",desc="运行状态 1.运行中 2.停止中")
    private int state;

    @ApiProperty(name="power",desc="项目作用域")
    private int power;

    @ApiProperty(name="color",desc="颜色")
    private int color;

    //流水线模板
    private String template;

    //收藏状态
    private int collect;

    //添加用户
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
