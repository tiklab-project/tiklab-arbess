package com.doublekit.pipeline.instance.entity;

import com.doublekit.dal.jpa.annotation.*;

/**
 * 流水线日志
 */

@Entity
@Table(name="pipeline_log")
public class LogEntity {

    //日志id
    @Id
    @GeneratorValue
    @Column(name = "log_id")
    private String logId;

    //运行日志
    @Column(name = "log_run_log",notNull = true)
    private String logRunLog;

    //拉取时间
    @Column(name = "log_code_time",notNull = true)
    private String logCodeTime;

    //拉取状态
    @Column(name = "log_code_state",notNull = true)
    private int  logCodeState;

    //打包时间
    @Column(name = "log_pack_time",notNull = true)
    private String logPackTime;

    //打包状态
    @Column(name = "log_pack_state",notNull = true)
    private int  logPackState;

    //部署时间
    @Column(name = "log_deploy_time",notNull = true)
    private String logDeployTime;

    //部署状态
    @Column(name = "log_deploy_state",notNull = true)
    private int logDeployState;

    //运行状态（30 ：成功  3：失败   其他）
    @Column(name = "log_run_status",notNull = true)
    private int logRunStatus ;

    //部署时间
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //测试时间
    @Column(name = "log_test_time",notNull = true)
    private int logTestTime;

    //测试状态
    @Column(name = "log_test_state",notNull = true)
    private int logTestState;


    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogRunLog() {
        return logRunLog;
    }

    public void setLogRunLog(String logRunLog) {
        this.logRunLog = logRunLog;
    }

    public String getLogCodeTime() {
        return logCodeTime;
    }

    public void setLogCodeTime(String logCodeTime) {
        this.logCodeTime = logCodeTime;
    }

    public int getLogCodeState() {
        return logCodeState;
    }

    public void setLogCodeState(int logCodeState) {
        this.logCodeState = logCodeState;
    }

    public String getLogPackTime() {
        return logPackTime;
    }

    public void setLogPackTime(String logPackTime) {
        this.logPackTime = logPackTime;
    }

    public int getLogPackState() {
        return logPackState;
    }

    public void setLogPackState(int logPackState) {
        this.logPackState = logPackState;
    }

    public String getLogDeployTime() {
        return logDeployTime;
    }

    public void setLogDeployTime(String logDeployTime) {
        this.logDeployTime = logDeployTime;
    }

    public int getLogDeployState() {
        return logDeployState;
    }

    public void setLogDeployState(int logDeployState) {
        this.logDeployState = logDeployState;
    }

    public int getLogRunStatus() {
        return logRunStatus;
    }

    public void setLogRunStatus(int logRunStatus) {
        this.logRunStatus = logRunStatus;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getLogTestTime() {
        return logTestTime;
    }

    public void setLogTestTime(int logTestTime) {
        this.logTestTime = logTestTime;
    }

    public int getLogTestState() {
        return logTestState;
    }

    public void setLogTestState(int logTestState) {
        this.logTestState = logTestState;
    }
}
