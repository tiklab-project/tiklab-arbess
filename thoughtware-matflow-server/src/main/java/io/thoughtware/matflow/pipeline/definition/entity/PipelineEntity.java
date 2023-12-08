package io.thoughtware.matflow.pipeline.definition.entity;

import io.thoughtware.dal.jpa.annotation.*;

/**
 * 流水线实体
 */
@Entity
@Table(name="pip_pipeline")
public class PipelineEntity {

    //流水线id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    //流水线名称
    @Column(name = "name")
    private String name;

    //流水线创建人
    @Column(name = "user_id")
    private String userId;

    //流水线创建时间
    @Column(name = "create_time")
    private String  createTime;

    //流水线类型 1.多任务 2.多阶段
    @Column(name = "type")
    private int type;

    //运行状态 1.运行中 2.停止中
    @Column(name = "state")
    private int state;

    //项目作用域 1.全局 2.项目
    @Column(name = "power")
    private int power;

    //颜色 1~5随机生成
    @Column(name="color")
    private int color;

    @Column(name="env_id")
    private String envId;

    @Column(name="group_id")
    private String groupId;

    public String getEnvId() {
        return envId;
    }

    public PipelineEntity setEnvId(String envId) {
        this.envId = envId;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public PipelineEntity setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
