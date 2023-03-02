package net.tiklab.matflow.pipeline.instance.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;
import net.tiklab.user.user.model.User;

import java.util.List;

/**
 * 流水线实例模型
 */

@ApiModel
@Join
@Mapper(targetAlias = "PipelineInstanceEntity")
public class PipelineInstance {

    @ApiProperty(name="instanceId",desc="构建实例id")
    private String instanceId;

    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    @ApiProperty(name="runWay",desc="运行方式(1.手动运行 2.触发器运行)")
    private int runWay;

    @ApiProperty(name="user",desc="用户",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    @ApiProperty(name="runStatus",desc="运行状态 1.失败 10.成功 20:停止")
    private int runStatus;

    @ApiProperty(name="runTime",desc="运行时间")
    private int runTime;

    @Mappings({
            @Mapping(source = "pipeline.id",target = "pipelineId")
    })
    @JoinQuery(key = "id")
    private Pipeline pipeline;

    @ApiProperty(name="sort",desc="顺序")
    private int sort;

    @ApiProperty(name="status",desc="状态 1.失败 10.成功 20:停止")
    private int status;

    @ApiProperty(name="findNumber",desc="构建次数")
    private int findNumber;

    @ApiProperty(name="findState",desc="运行状态（0.运行完成，1.运行中）")
    private int findState;

    public PipelineInstance() {
    }

    /**
     * 初始化信息
     * @param createTime 时间
     * @param runWay 运行方式
     * @param userId 用户id
     * @param pipelineId 流水线id
     */
    public PipelineInstance(String createTime, int runWay, String userId, String pipelineId) {
        this.createTime = createTime;
        this.runWay = runWay;
        User user = new User();
        user.setId(userId);
        this.user = user;
        this.pipeline = new Pipeline(pipelineId);
    }

    //执行时间
    private List<Integer> timeList;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public List<Integer> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Integer> timeList) {
        this.timeList = timeList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getRunWay() {
        return runWay;
    }

    public void setRunWay(int runWay) {
        this.runWay = runWay;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public int getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getFindNumber() {
        return findNumber;
    }

    public void setFindNumber(int findNumber) {
        this.findNumber = findNumber;
    }

    public int getFindState() {
        return findState;
    }

    public void setFindState(int findState) {
        this.findState = findState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
