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
    @Column(name = "history_create_time",notNull = true)
    private String historyCreateTime;

    //构建方式
    @Column(name = "history_way",notNull = true)
    private int historyWay;

    //分支
    @Column(name = "history_branch",notNull = true)
    private String historyBranch;

    //执行人
    @Column(name = "pipeline_name",notNull = true)
    private String pipelineName;

    //日志id
    @Column(name = "log_id",notNull = true)
    private String logId;

    //状态
    @Column(name = "history_status",notNull = true)
    private int historyStatus;

    //执行时间
    @Column(name = "history_time",notNull = true)
    private int historyTime;

    //流水线id
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;



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

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public int getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(int historyStatus) {
        this.historyStatus = historyStatus;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public int getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(int historyTime) {
        this.historyTime = historyTime;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
