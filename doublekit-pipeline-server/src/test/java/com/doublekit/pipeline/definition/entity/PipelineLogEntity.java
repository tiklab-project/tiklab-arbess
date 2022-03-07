package com.doublekit.pipeline.definition.entity;


import com.doublekit.dal.jpa.annotation.*;

/**
 * 流水线日志
 */

@Entity
@Table(name="pipeline_log")
public class PipelineLogEntity {

    //日志id
    @Id
    @GeneratorValue
    @Column(name = "log_id")
    private String logId;

    //构建状态
    @Column(name = "log_implement_state",notNull = true)
    private int logImplementState;

    //执行时间
    @Column(name = "log_implement_time",notNull = true)
    private String logImplementTime;

    //日志地址
    @Column(name = "log_log_address")
    private String historyWay;

    //历史信息id
    @Column(name = "history_id",notNull = true)
    private String historyId;



    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }



    public String getLogImplementTime() {
        return logImplementTime;
    }

    public void setLogImplementTime(String logImplementTime) {
        this.logImplementTime = logImplementTime;
    }

    public int getLogImplementState() {
        return logImplementState;
    }

    public void setLogImplementState(int logImplementState) {
        this.logImplementState = logImplementState;
    }

    public String getHistoryWay() {
        return historyWay;
    }

    public void setHistoryWay(String historyWay) {
        this.historyWay = historyWay;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }
}
