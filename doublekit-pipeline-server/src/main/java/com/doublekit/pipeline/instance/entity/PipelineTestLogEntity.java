package com.doublekit.pipeline.instance.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_test_log")
public class PipelineTestLogEntity {

    //日志id
    @Id
    @GeneratorValue
    @Column(name = "test_id")
    private String logTestId;

    //运行时间
    @Column(name = "test_run_time",notNull = true)
    private int testRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @Column(name = "test_run_state",notNull = true)
    private int testRunStatus ;

    //运行日志
    @Column(name = "test_run_log",notNull = true)
    private String testRunLog;


    public String getLogTestId() {
        return logTestId;
    }

    public void setLogTestId(String logTestId) {
        this.logTestId = logTestId;
    }

    public int getTestRunTime() {
        return testRunTime;
    }

    public void setTestRunTime(int testRunTime) {
        this.testRunTime = testRunTime;
    }

    public int getTestRunStatus() {
        return testRunStatus;
    }

    public void setTestRunStatus(int testRunStatus) {
        this.testRunStatus = testRunStatus;
    }

    public String getTestRunLog() {
        return testRunLog;
    }

    public void setTestRunLog(String testRunLog) {
        this.testRunLog = testRunLog;
    }
}
