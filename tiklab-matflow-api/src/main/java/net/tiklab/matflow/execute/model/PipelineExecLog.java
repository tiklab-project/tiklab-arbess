package net.tiklab.matflow.execute.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

/**
 * 流水线日志
 */

@ApiModel
@Join
@Mapper(targetAlias = "PipelineExecLogEntity")
public class PipelineExecLog {

    //日志id
    @ApiProperty(name="logId",desc="日志id")
    private String logId;

    //historyId
    @ApiProperty(name = "historyId",desc = "历史Id")
    private String historyId;

    //运行类型
    @ApiProperty(name = "taskType",desc = "运行类型")
    private int taskType ;

    //执行顺序
    @ApiProperty(name = "taskSort",desc = "执行顺序")
    private int taskSort ;

    @ApiProperty(name = "taskName",desc = "任务名称")
    private String taskName;

    //执行名称
    @ApiProperty(name = "taskSort",desc = "执行顺序")
    private String taskAlias;

    //运行日志
    @ApiProperty(name = "logAddress",desc = "运行日志")
    private String logAddress;

    //运行时间
    @ApiProperty(name = "runTime",desc = "运行时间")
    private int runTime;

    //运行状态
    @ApiProperty(name = "runState",desc = "运行状态")
    private int runState;

    @ApiProperty(name="stageId",desc="阶段id")
    private String stagesId;


    private String runLog;

    public PipelineExecLog() {
    }

    public PipelineExecLog(String historyId, int taskType, int taskSort) {
        this.historyId = historyId;
        this.taskType = taskType;
        this.taskSort = taskSort;
    }

    public PipelineExecLog(String historyId, int taskType, int taskSort, String stagesId) {
        this.historyId = historyId;
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

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
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

    public String getTaskAlias() {
        return taskAlias;
    }

    public void setTaskAlias(String taskAlias) {
        this.taskAlias = taskAlias;
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
