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
    private String id;

    /**
     * @pi.name:name
     * @pi.dataType:string
     * @pi.desc:流水线名称
     * @pi.value:name
     */
    private String name;

    /**
     * @pi.model:User
     * @pi.desc:用户
     */
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    /**
     * @pi.name:createTime
     * @pi.dataType:string
     * @pi.desc:流水线创建时间
     * @pi.value:createTime
     */
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
     * @pi.desc:运行状态 1.运行中 2.停止中
     * @pi.value:1
     */
    private int state;

    /**
     * @pi.name:power
     * @pi.dataType:Integer
     * @pi.desc:项目作用域 1.全局 2.项目
     * @pi.value:1
     */
    private int power;

    /**
     * @pi.name:color
     * @pi.dataType:Integer
     * @pi.desc:颜色 1~5随机生成
     * @pi.value:2
     */
    private int color;

    /**
     * @pi.name:template
     * @pi.dataType:string
     * @pi.desc:流水线模板
     * @pi.value:template
     */
    private String template;

    /**
     * @pi.name:collect
     * @pi.dataType:Integer
     * @pi.desc:收藏 0.未收藏 1.收藏
     * @pi.value:2
     */
    private int collect;

    /**
     * @pi.model:userList
     * @pi.desc:流水线成员
     */
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
