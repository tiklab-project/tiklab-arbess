package net.tiklab.matflow.definition.model;


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
    @ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    //流水线名称
    @ApiProperty(name="pipelineName",desc="流水线名称")
    private String pipelineName;

    //流水线创建人
    @ApiProperty(name="user",desc="认证配置",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    //流水线创建时间
    @ApiProperty(name="pipelineCreateTime",desc="流水线创建时间",required = true)
    private String pipelineCreateTime;

    //流水线类型
    @ApiProperty(name="pipelineType",desc="流水线类型",required = true)
    private int pipelineType;

    //收藏状态
    private int pipelineCollect;

    //运行状态
    @ApiProperty(name="pipelineCollect",desc="运行状态 1.运行中 2.停止中",required = true)
    private int pipelineState;

    @ApiProperty(name="pipelinePower",desc="项目作用域",required = true)
    private int pipelinePower;

    @ApiProperty(name="color",desc="颜色",required = true)
    private int color;

    //流水线模板
    private String pipelineTemplate;

    //添加用户
    @ApiProperty(name="userList",desc="权限",required = true)
    private List<PatchUser> userList;


    public Pipeline(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public Pipeline() {
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPipelineCreateTime() {
        return pipelineCreateTime;
    }

    public void setPipelineCreateTime(String pipelineCreateTime) {
        this.pipelineCreateTime = pipelineCreateTime;
    }

    public int getPipelineType() {
        return pipelineType;
    }

    public void setPipelineType(int pipelineType) {
        this.pipelineType = pipelineType;
    }

    public int getPipelineCollect() {
        return pipelineCollect;
    }

    public void setPipelineCollect(int pipelineCollect) {
        this.pipelineCollect = pipelineCollect;
    }

    public int getPipelineState() {
        return pipelineState;
    }

    public void setPipelineState(int pipelineState) {
        this.pipelineState = pipelineState;
    }

    public int getPipelinePower() {
        return pipelinePower;
    }

    public void setPipelinePower(int pipelinePower) {
        this.pipelinePower = pipelinePower;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<PatchUser> getUserList() {
        return userList;
    }

    public void setUserList(List<PatchUser> userList) {
        this.userList = userList;
    }

    public String getPipelineTemplate() {
        return pipelineTemplate;
    }

    public void setPipelineTemplate(String pipelineTemplate) {
        this.pipelineTemplate = pipelineTemplate;
    }
}
