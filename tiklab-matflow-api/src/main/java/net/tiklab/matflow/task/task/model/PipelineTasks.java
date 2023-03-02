package net.tiklab.matflow.task.task.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

/**
 * 流水线配置顺序模型
 */

@ApiModel
@Join
@Mapper(targetAlias = "PipelineTasksEntity")
public class PipelineTasks {

    @ApiProperty(name="taskId",desc="配置id")
    private String taskId;

    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    @ApiProperty(name="taskType",
            desc= "类型1-10:源码,10-20:测试,20-30:构建,30-40:部署,40-50:代码扫描,50-60:推送制品")
    private int taskType;

    @ApiProperty(name="taskSort",desc="顺序")
    private int taskSort;

    @ApiProperty(name="values",desc="任务")
    private Object values;

    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    private String pipelineId;

    @ApiProperty(name="stages",desc="阶段",eg="@selectOne")
    private String stagesId;


    public PipelineTasks() {
    }

    public PipelineTasks(String createTime, int taskType) {
        this.createTime = createTime;
        this.taskType = taskType;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }

    public Object getValues() {
        return values;
    }

    public void setValues(Object values) {
        this.values = values;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }



}
