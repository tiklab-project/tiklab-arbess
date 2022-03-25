package com.doublekit.pipeline.instance.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_code_log")
public class PipelineCodeLogEntity {

    //日志id
    @Id
    @GeneratorValue
    @Column(name = "code_id")
    private String codeId;

    //运行日志
    @Column(name = "code_run_time",notNull = true)
    private String codeRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @Column(name = "code_run_state",notNull = true)
    private int codeRunStatus ;

    //运行时间
    @Column(name = "code_run_log",notNull = true)
    private int codeRunLog;


    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeRunTime() {
        return codeRunTime;
    }

    public void setCodeRunTime(String codeRunTime) {
        this.codeRunTime = codeRunTime;
    }

    public int getCodeRunStatus() {
        return codeRunStatus;
    }

    public void setCodeRunStatus(int codeRunStatus) {
        this.codeRunStatus = codeRunStatus;
    }

    public int getCodeRunLog() {
        return codeRunLog;
    }

    public void setCodeRunLog(int codeRunLog) {
        this.codeRunLog = codeRunLog;
    }
}
