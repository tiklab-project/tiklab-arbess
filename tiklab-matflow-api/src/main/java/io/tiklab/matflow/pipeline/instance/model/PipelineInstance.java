package io.tiklab.matflow.pipeline.instance.model;


import io.tiklab.beans.annotation.Mapper;
import io.tiklab.beans.annotation.Mapping;
import io.tiklab.beans.annotation.Mappings;
import io.tiklab.join.annotation.Join;
import io.tiklab.join.annotation.JoinQuery;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
import io.tiklab.user.user.model.User;

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
    private String runStatus;

    @ApiProperty(name="runTime",desc="运行时间")
    private int runTime;

    @Mappings({
            @Mapping(source = "pipeline.id",target = "pipelineId")
    })
    @JoinQuery(key = "id")
    private Pipeline pipeline;

    @ApiProperty(name="findNumber",desc="构建次数")
    private int findNumber;

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

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getFindNumber() {
        return findNumber;
    }

    public void setFindNumber(int findNumber) {
        this.findNumber = findNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
