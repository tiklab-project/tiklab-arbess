package io.tiklab.matflow.pipeline.definition.model;

import io.tiklab.join.annotation.Join;
import io.tiklab.user.user.model.User;

/**
 * @pi.model:io.tiklab.matflow.pipeline.definition.model.PipelineExecMessage
 * @desc:流水线执行信息模型
 */

@Join
public class PipelineExecMessage {

    /**
     * @pi.name:id
     * @pi.dataType:string
     * @pi.desc:流水线id
     * @pi.value:111
     */
    private String id;

    /**
     * @pi.name:collect
     * @pi.dataType:Integer
     * @pi.desc:收藏状态 0.未收藏 1.收藏
     * @pi.value:1
     */
    private int collect;

    /**
     * @pi.name:buildStatus
     * @pi.dataType:string
     * @pi.desc:最近构建状态
     * @pi.value:buildStatus
     */
    private String buildStatus;

    /**
     * @pi.name:name
     * @pi.dataType:string
     * @pi.desc:流水线名称
     * @pi.value:name
     */
    private String name;

    /**
     * @pi.name:lastBuildTime
     * @pi.dataType:string
     * @pi.desc:最近构建时间
     * @pi.value:lastBuildTime
     */
    private String lastBuildTime;

    /**
     * @pi.name:state
     * @pi.dataType:Integer
     * @pi.desc:运行状态 (1.运行中 2.停止中)
     * @pi.value:1
     */
    private int state;

    /**
     * @pi.name:color
     * @pi.dataType:Integer
     * @pi.desc:颜色
     * @pi.value:2
     */
    private int color;

    /**
     * @pi.name:type
     * @pi.dataType:Integer
     * @pi.desc:流水线类型(1.单任务 2.多任务)
     * @pi.value:1
     */
    private int type;

    /**
     * @pi.name:power
     * @pi.dataType:Integer
     * @pi.desc:权限(1.全局 2.私有)
     * @pi.value:1
     */
    private int power;

    /**
     * @pi.model:user
     * @pi.desc:用户(负责人)
     */
    private User user;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }


    public String getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(String buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastBuildTime() {
        return lastBuildTime;
    }

    public void setLastBuildTime(String lastBuildTime) {
        this.lastBuildTime = lastBuildTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getExecUser() {
        return execUser;
    }

    public void setExecUser(User execUser) {
        this.execUser = execUser;
    }
}
