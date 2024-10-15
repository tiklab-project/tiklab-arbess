package io.tiklab.arbess.task.task.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;



/**
 * 流水线配置顺序模型
 */

//@ApiModel
@Join
@Mapper(targetAlias = "TasksEntity")
public class Tasks {

    //@ApiProperty(name="taskId",desc="配置id")
    private String taskId;

    //@ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    //@ApiProperty(name="taskType",desc= "类型1-10:源码,10-20:测试,20-30:构建,30-40:部署,40-50:代码扫描,50-60:推送制品")
    private String taskType;

    //@ApiProperty(name="taskSort",desc="顺序")
    private int taskSort;

    //@ApiProperty(name="taskName",desc="顺序")
    private String taskName;

    //@ApiProperty(name="values",desc="任务")
    private Object values;

    //@ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    private String pipelineId;

    //@ApiProperty(name="postprocessId",desc="后置处理id",eg="@selectOne")
    private String postprocessId;;

    //@ApiProperty(name="stageId",desc="阶段",eg="@selectOne")
    private String stageId;

    //@ApiProperty(name="task",desc="任务",eg="@selectOne")
    private Object task;

    // 执行实例id
    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Object getTask() {
        return task;
    }

    public void setTask(Object task) {
        this.task = task;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Tasks() {
    }

    public String getPostprocessId() {
        return postprocessId;
    }

    public void setPostprocessId(String postprocessId) {
        this.postprocessId = postprocessId;
    }

    public Tasks(String createTime, String taskType) {
        this.createTime = createTime;
        this.taskType = taskType;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public Object getValues() {
        return values;
    }

    public void setValues(Object values) {
        this.values = values;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
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
