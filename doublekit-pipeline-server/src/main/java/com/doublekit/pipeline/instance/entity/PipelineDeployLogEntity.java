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

    //运行时间
    @Column(name = "deploy_run_time",notNull = true)
    private int deployRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @Column(name = "deploy_run_state",notNull = true)
    private int deployRunStatus ;

    //运行日志
    @Column(name = "deploy_run_log",notNull = true)
    private String deployRunLog;


    public String getLogDeployId() {
        return logDeployId;
    }

    public void setLogDeployId(String logDeployId) {
        this.logDeployId = logDeployId;
    }

    public int getDeployRunTime() {
        return deployRunTime;
    }

    public void setDeployRunTime(int deployRunTime) {
        this.deployRunTime = deployRunTime;
    }

    public int getDeployRunStatus() {
        return deployRunStatus;
    }

    public void setDeployRunStatus(int deployRunStatus) {
        this.deployRunStatus = deployRunStatus;
    }

    public String getDeployRunLog() {
        return deployRunLog;
    }

    public void setDeployRunLog(String deployRunLog) {
        this.deployRunLog = deployRunLog;
    }
}
