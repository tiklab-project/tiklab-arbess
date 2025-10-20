package io.tiklab.arbess.pipeline.definition.model;


import io.tiklab.arbess.setting.env.model.Env;
import io.tiklab.arbess.setting.group.model.Group;
import io.tiklab.postin.annotation.ApiProperty;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;



import io.tiklab.privilege.role.model.PatchUser;
import io.tiklab.toolkit.join.annotation.JoinField;
import io.tiklab.user.user.model.User;

import java.util.List;

/**
 * @pi.model:io.tiklab.matflow.pipeline.definition.model.Pipeline
 * @desc:流水线模型
 */
@Join
@Mapper
public class Pipeline {

    /**
     * @pi.name:id
     * @pi.dataType:string
     * @pi.desc:流水线id
     * @pi.value:11111
     */
    @ApiProperty(name = "pipelineId",desc = "流水线id")
    private String id;

    /**
     * @pi.name:name
     * @pi.dataType:string
     * @pi.desc:流水线名称
     * @pi.value:name
     */
    @ApiProperty(name = "name",desc = "流水线名称")
    private String name;

    /**
     * @pi.model:User
     * @pi.desc:用户
     */
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinField(key = "id")
    @ApiProperty(name = "user",desc = "流水线用户")
    private User user;


    /**
     * @pi.model:Env
     * @pi.desc:流水线环境
     */
    @Mappings({
            @Mapping(source = "env.id",target = "envId")
    })
    @JoinField(key = "id")
    @ApiProperty(name = "env",desc = "流水线环境")
    private Env env;

    /**
     * @pi.model:Env
     * @pi.desc:流水线组
     */
    @Mappings({
            @Mapping(source = "group.id",target = "groupId")
    })
    @JoinField(key = "id")
    @ApiProperty(name = "group",desc = "流水线组")
    private Group group;

    /**
     * @pi.name:createTime
     * @pi.dataType:string
     * @pi.desc:流水线创建时间
     * @pi.value:createTime
     */
    @ApiProperty(name = "createTime",desc = "流水线创建时间")
    private String createTime;

    /**
     * @pi.name:type
     * @pi.dataType:Integer
     * @pi.desc:流水线类型 1.多任务 2.多阶段
     * @pi.value: 1
     */
    private int type;

    /**
     * @pi.name:state
     * @pi.dataType:Integer
     * @pi.desc:运行状态 1.未运行 2.运行中
     * @pi.value:1
     */
    @ApiProperty(name = "state",desc = "运行状态 1.未运行 2.运行中")
    private int state;

    /**
     * @pi.name:power
     * @pi.dataType:Integer
     * @pi.desc:项目作用域 1.全局 2.项目
     * @pi.value:1
     */
    @ApiProperty(name = "power",desc = "1.全局 2.项目")
    private int power;

    /**
     * @pi.name:color
     * @pi.dataType:Integer
     * @pi.desc:颜色 1~5随机生成
     * @pi.value:2
     */
    private int color;

    /**
     * @pi.name:templateList
     * @pi.dataType:java.util.List<>
     * @pi.desc:流水线模板
     * @pi.value:[]
     */
    private List<PipelineTemplate> templateList;

    /**
     * @pi.name:collect
     * @pi.dataType:Integer
     * @pi.desc:收藏 0.未收藏 1.收藏
     * @pi.value:2
     */
    @ApiProperty(name = "collect",desc = "收藏 0.未收藏 1.收藏")
    private int collect;

    /**
     * @pi.model:userList
     * @pi.desc:流水线成员
     */
    private List<PatchUser> userList;


    // 以下为统计信息


    /**
     * @pi.model:execUser
     * @pi.desc:用户(执行人)
     */
    private User execUser;

    /**
     * @pi.name:number
     * @pi.dataType:Integer
     * @pi.desc:执行次数
     * @pi.value:2
     */
    private Integer number;

    /**
     * @pi.name:instanceId
     * @pi.dataType:string
     * @pi.desc:实例id
     * @pi.value:instanceId
     */
    private String instanceId;


    /**
     * @pi.name:buildStatus
     * @pi.dataType:string
     * @pi.desc:最近构建状态
     * @pi.value:buildStatus
     */
    private String buildStatus;


    /**
     * @pi.name:lastBuildTime
     * @pi.dataType:string
     * @pi.desc:最近构建时间
     * @pi.value:lastBuildTime
     */
    private String lastBuildTime;


    /**
     * @pi.name:isExec
     * @pi.dataType:boolean
     * @pi.desc:是否可以执行
     * @pi.value:true
     */
    private Boolean isExec;

    /**
     * @pi.model:PipelinePermissions
     * @pi.desc:流水线权限
     */
    private PipelinePermissions permissions;

    public PipelinePermissions getPermissions() {
        return permissions;
    }

    public void setPermissions(PipelinePermissions permissions) {
        this.permissions = permissions;
    }

    public List<PipelineTemplate> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<PipelineTemplate> templateList) {
        this.templateList = templateList;
    }

    public Boolean getExec() {
        return isExec;
    }

    public void setExec(Boolean exec) {
        isExec = exec;
    }

    public Pipeline() {
    }

    public Pipeline(String id) {
        this.id = id;
    }

    public Env getEnv() {
        return env;
    }

    public Pipeline setEnv(Env env) {
        this.env = env;
        return this;
    }

    public Group getGroup() {
        return group;
    }

    public Pipeline setGroup(Group group) {
        this.group = group;
        return this;
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

    public List<PatchUser> getUserList() {
        return userList;
    }

    public void setUserList(List<PatchUser> userList) {
        this.userList = userList;
    }


    public User getExecUser() {
        return execUser;
    }

    public void setExecUser(User execUser) {
        this.execUser = execUser;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(String buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getLastBuildTime() {
        return lastBuildTime;
    }

    public void setLastBuildTime(String lastBuildTime) {
        this.lastBuildTime = lastBuildTime;
    }
}
