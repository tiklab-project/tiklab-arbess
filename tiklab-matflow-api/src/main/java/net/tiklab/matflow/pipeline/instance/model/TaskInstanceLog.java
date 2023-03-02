package net.tiklab.matflow.pipeline.instance.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

/**
 * 任务实例模型
 */

@ApiModel
@Join
@Mapper(targetAlias = "TaskInstanceLogEntity")
public class TaskInstanceLog {

    @ApiProperty(name="logId",desc="日志id")
    private String logId;

    @ApiProperty(name = "instanceId",desc = "历史Id")
    private String instanceId;

    @ApiProperty(name = "taskType",desc = "运行类型")
    private int taskType ;

    @ApiProperty(name = "taskSort",desc = "执行顺序")
    private int taskSort ;

    @ApiProperty(name = "taskName",desc = "任务名称")
    private String taskName;

    @ApiProperty(name = "logAddress",desc = "运行日志地址")
    private String logAddress;

    @ApiProperty(name = "runTime",desc = "运行时间")
    private int runTime;

    @ApiProperty(name = "runState",desc = "运行状态1.失败 10.成功 20.停止")
    private int runState;

    @ApiProperty(name="stageId",desc="阶段id")
    private String stagesId;


    private String runLog;

    public TaskInstanceLog() {
    }

    public TaskInstanceLog(String instanceId, int taskType, int taskSort) {
        this.instanceId = instanceId;
        this.taskType = taskType;
        this.taskSort = taskSort;
    }

    public TaskInstanceLog(String instanceId, int taskType, int taskSort, String stagesId) {
        this.instanceId = instanceId;
        this.taskType = taskType;
        this.taskSort = taskSort;
        this.stagesId = stagesId;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
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

    public int getRunState() {
        return runState;
    }

    public void setRunState(int runState) {
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
}
