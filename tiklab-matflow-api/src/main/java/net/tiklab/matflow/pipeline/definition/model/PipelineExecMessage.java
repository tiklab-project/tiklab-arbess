package net.tiklab.matflow.pipeline.definition.model;


import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.user.user.model.User;

/**
 * 流水线执行信息模型
 */

@ApiModel
@Join
public class PipelineExecMessage {

    private String id;

    //收藏状态
    private int collect;

    //最近构建状态
    private int buildStatus;

    //流水线名称
    private String name;

    //最近构建时间
    private String lastBuildTime;

    //运行状态 (1.运行中 2.停止中)
    private int state;

    //颜色
    private int color;

    //流水线类型(1.单任务 2.多任务)
    private int type;

    //权限(1.全局 2.私有)
    private int power;

    //用户(负责人)
    private User user;

    //用户(执行人)
    private User execUser;

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

    public int getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(int buildStatus) {
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
