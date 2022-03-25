package com.doublekit.pipeline.instance.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_deploy_log")
public class PipelineDeployLogEntity {


    //日志id
    @Id
    @GeneratorValue
    @Column(name = "deploy_id")
    private String logDeployId;

    //运行日志
    @Column(name = "deploy_run_time",notNull = true)
    private String deployRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @Column(name = "deploy_run_state",notNull = true)
    private int deployRunStatus ;

    //运行时间
    @Column(name = "deploy_run_log",notNull = true)
    private int deployRunLog;


    public String getLogDeployId() {
        return logDeployId;
    }

    public void setLogDeployId(String logDeployId) {
        this.logDeployId = logDeployId;
    }

    public String getDeployRunTime() {
        return deployRunTime;
    }

    public void setDeployRunTime(String deployRunTime) {
        this.deployRunTime = deployRunTime;
    }

    public int getDeployRunStatus() {
        return deployRunStatus;
    }

    public void setDeployRunStatus(int deployRunStatus) {
        this.deployRunStatus = deployRunStatus;
    }

    public int getDeployRunLog() {
        return deployRunLog;
    }

    public void setDeployRunLog(int deployRunLog) {
        this.deployRunLog = deployRunLog;
    }
}
