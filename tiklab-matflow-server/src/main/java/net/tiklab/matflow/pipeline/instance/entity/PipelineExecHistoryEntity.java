package net.tiklab.matflow.pipeline.instance.entity;


import net.tiklab.dal.jpa.annotation.*;

/**
 *流水线历史
 */

@Entity
@Table(name="pip_pipeline_history")
public class PipelineExecHistoryEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "history_id")
    private String historyId;

    //创建构建时间
    @Column(name = "create_time",notNull = true)
    private String createTime;

    //构建方式
    @Column(name = "run_way",notNull = true)
    private int runWay;

    //运行日志
    @Column(name = "run_log",notNull = true)
    private String runLog;

    //执行人
    @Column(name = "user_id",notNull = true)
    private String userId;

    //状态
    @Column(name = "run_status",notNull = true)
    private int runStatus;

    //执行时间
    @Column(name = "run_time",notNull = true)
    private int runTime;

    //流水线
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //判断是否正在执行
    @Column(name = "find_state", notNull = true)
    private int findState;

    //构建次数
    @Column(name = "find_number", notNull = true)
    private int findNumber;

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getRunWay() {
        return runWay;
    }

    public void setRunWay(int runWay) {
        this.runWay = runWay;
    }

    public String getRunLog() {
        return runLog;
    }

    public void setRunLog(String runLog) {
        this.runLog = runLog;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getFindState() {
        return findState;
    }

    public void setFindState(int findState) {
        this.findState = findState;
    }

    public int getFindNumber() {
        return findNumber;
    }

    public void setFindNumber(int findNumber) {
        this.findNumber = findNumber;
    }
}
