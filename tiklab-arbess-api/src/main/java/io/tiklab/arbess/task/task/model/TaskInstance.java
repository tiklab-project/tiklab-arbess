package io.tiklab.arbess.task.task.model;


import io.tiklab.arbess.task.deploy.model.TaskDeployInstance;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.List;


/**
 * 任务实例模型
 */

//@ApiModel
@Join
@Mapper
public class TaskInstance {

    //@ApiProperty(name="id",desc="日志id")
    private String id;

    //@ApiProperty(name = "instanceId",desc = "历史Id")
    private String instanceId;

    //@ApiProperty(name = "taskType",desc = "运行类型")
    private String taskType ;

    //@ApiProperty(name = "taskSort",desc = "执行顺序")
    private int taskSort ;

    //@ApiProperty(name = "taskName",desc = "任务名称")
    private String taskName;

    //@ApiProperty(name = "logAddress",desc = "运行日志地址")
    private String logAddress;

    //@ApiProperty(name = "runTime",desc = "运行时间")
    private int runTime;

    //@ApiProperty(name = "runTimeDate",desc = "运行时间")
    private String runTimeDate;

    //@ApiProperty(name = "runState",desc = "运行状态 error.失败 success.成功 halt.停止 wait.等待")
    private String runState;

    //@ApiProperty(name="stageId",desc="阶段id")
    private String stagesId;

    //@ApiProperty(name="postprocessId",desc="后置任务id")
    private String postprocessId;

    //@ApiProperty(name="runLog",desc="运行日志")
    private String runLog;

    // 部署实例日志
    private List<TaskDeployInstance> deployInstanceList;

    public List<TaskDeployInstance> getDeployInstanceList() {
        return deployInstanceList;
    }

    public void setDeployInstanceList(List<TaskDeployInstance> deployInstanceList) {
        this.deployInstanceList = deployInstanceList;
    }

    public TaskInstance() {
    }

    public TaskInstance(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public String getLogAddress() {
        return logAddress;
    }

    public void setLogAddress(String logAddress) {
        this.logAddress = logAddress;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getRunState() {
        return runState;
    }

    public void setRunState(String runState) {
        this.runState = runState;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRunLog() {
        return runLog;
    }

    public void setRunLog(String runLog) {
        this.runLog = runLog;
    }

    public String getPostprocessId() {
        return postprocessId;
    }

    public void setPostprocessId(String postprocessId) {
        this.postprocessId = postprocessId;
    }

    public String getRunTimeDate() {
        return runTimeDate;
    }

    public void setRunTimeDate(String runTimeDate) {
        this.runTimeDate = runTimeDate;
    }

    @Override
    public String toString() {
        return "TaskInstance{" +
                "id='" + id + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", taskType=" + taskType +
                ", taskSort=" + taskSort +
                ", taskName='" + taskName + '\'' +
                ", logAddress='" + logAddress + '\'' +
                ", runTime=" + runTime +
                ", runTimeDate='" + runTimeDate + '\'' +
                ", runState='" + runState + '\'' +
                ", stagesId='" + stagesId + '\'' +
                ", postprocessId='" + postprocessId + '\'' +
                ", runLog='" + runLog + '\'' +
                '}';
    }
}
