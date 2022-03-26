package com.doublekit.pipeline.instance.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_code_log")
public class PipelineCodeLogEntity {

    //日志id
    @Id
    @GeneratorValue
    @Column(name = "code_id")
    private String logCodeId;

    //运行时间
    @Column(name = "code_run_time",notNull = true)
    private int codeRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @Column(name = "code_run_state",notNull = true)
    private int codeRunStatus ;

    //运行日志
    @Column(name = "code_run_log",notNull = true)
    private String codeRunLog;


    public String getLogCodeId() {
        return logCodeId;
    }

    public void setLogCodeId(String logCodeId) {
        this.logCodeId = logCodeId;
    }

    public int getCodeRunTime() {
        return codeRunTime;
    }

    public void setCodeRunTime(int codeRunTime) {
        this.codeRunTime = codeRunTime;
    }

    public int getCodeRunStatus() {
        return codeRunStatus;
    }

    public void setCodeRunStatus(int codeRunStatus) {
        this.codeRunStatus = codeRunStatus;
    }

    public String getCodeRunLog() {
        return codeRunLog;
    }

    public void setCodeRunLog(String codeRunLog) {
        this.codeRunLog = codeRunLog;
    }
}
