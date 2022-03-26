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
    @Column(name = "history_time",notNull = true)
    private String historyCreateTime;

    //构建方式
    @Column(name = "history_way",notNull = true)
    private int historyWay;

    //分支
    @Column(name = "history_branch",notNull = true)
    private String historyBranch;

    //流水线id
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //日志id
    @Column(name = "log_id",notNull = true)
    private String logId;


    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getHistoryCreateTime() {
        return historyCreateTime;
    }

    public void setHistoryCreateTime(String historyCreateTime) {
        this.historyCreateTime = historyCreateTime;
    }

    public int getHistoryWay() {
        return historyWay;
    }

    public void setHistoryWay(int historyWay) {
        this.historyWay = historyWay;
    }

    public String getHistoryBranch() {
        return historyBranch;
    }

    public void setHistoryBranch(String historyBranch) {
        this.historyBranch = historyBranch;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

}
