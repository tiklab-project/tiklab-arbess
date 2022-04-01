package com.doublekit.pipeline.instance.entity;

import com.doublekit.dal.jpa.annotation.*;

/**
 * 流水线日志
 */

@Entity
@Table(name="pipeline_log")
public class PipelineExecLogEntity {

    //日志id
    @Id
    @GeneratorValue
    @Column(name = "log_id")
    private String logId;

    //运行日志
    @Column(name = "log_run_log",notNull = true)
    private String logRunLog;

    //运行状态（30 ：成功  1：失败   0 :其他）
    @Column(name = "log_run_status",notNull = true)
    private int logRunStatus ;

    //运行时间
    @Column(name = "log_run_time",notNull = true)
    private int logRunTime;

    //codeId
    @Column(name = "log_code_id",notNull = true)
    private String logCodeId;

    //testId
    @Column(name = "log_test_id",notNull = true)
    private String logTestId;

    //structureId
    @Column(name = "log_structure_id",notNull = true)
    private String logStructureId;

    //deployId
    @Column(name = "log_deploy_id",notNull = true)
    private String logDeployId;

    //pipelineId
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;


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

    public int getLogRunStatus() {
        return logRunStatus;
    }

    public void setLogRunStatus(int logRunStatus) {
        this.logRunStatus = logRunStatus;
    }

    public int getLogRunTime() {
        return logRunTime;
    }

    public void setLogRunTime(int logRunTime) {
        this.logRunTime = logRunTime;
    }

    public String getLogCodeId() {
        return logCodeId;
    }

    public void setLogCodeId(String logCodeId) {
        this.logCodeId = logCodeId;
    }

    public String getLogTestId() {
        return logTestId;
    }

    public void setLogTestId(String logTestId) {
        this.logTestId = logTestId;
    }

    public String getLogStructureId() {
        return logStructureId;
    }

    public void setLogStructureId(String logStructureId) {
        this.logStructureId = logStructureId;
    }

    public String getLogDeployId() {
        return logDeployId;
    }

    public void setLogDeployId(String logDeployId) {
        this.logDeployId = logDeployId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
