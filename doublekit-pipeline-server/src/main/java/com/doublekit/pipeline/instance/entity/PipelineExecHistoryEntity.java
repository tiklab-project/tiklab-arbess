package com.doublekit.pipeline.instance.entity;


import com.doublekit.dal.jpa.annotation.*;

/**
 *流水线历史
 */

@Entity
@Table(name="pipeline_history")
public class PipelineExecHistoryEntity {

    //id
    @Id
    @Column(name = "history_id")
    private int historyId;

    //创建构建时间
    @Column(name = "create_time",notNull = true)
    private String createTime;

    //构建方式
    @Column(name = "run_way",notNull = true)
    private int runWay;

    //分支
    @Column(name = "run_log",notNull = true)
    private String runLog;

    //执行人
    @Column(name = "pipeline_name",notNull = true)
    private String pipelineName;

    //日志id
    @Column(name = "log_id",notNull = true)
    private String logId;

    //状态
    @Column(name = "run_status",notNull = true)
    private int runStatus;

    //执行时间
    @Column(name = "run_time",notNull = true)
    private int runTime;


    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
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

    public String getRunLog() {
        return runLog;
    }

    public void setRunLog(String runLog) {
        this.runLog = runLog;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
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
}
