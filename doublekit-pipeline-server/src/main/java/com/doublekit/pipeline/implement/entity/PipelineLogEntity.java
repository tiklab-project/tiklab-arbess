package com.doublekit.pipeline.implement.entity;


import com.doublekit.dal.jpa.mapper.annotation.*;

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

    //日志地址
    @Column(name = "log_address",notNull = true)
    private String logAddress;

    //拉取时间
    @Column(name = "log_code_time",notNull = true)
    private String logCodeTime;

    //拉取状态
    @Column(name = "log_code_state",notNull = true)
    private int  logCodeState;

    //打包时间
    @Column(name = "log_pack_time",notNull = true)
    private String logPackTime;

    //打包状态
    @Column(name = "log_pack_state",notNull = true)
    private int  logPackState;

    //部署时间
    @Column(name = "log_deploy_time",notNull = true)
    private String logDeployTime;

    //部署状态
    @Column(name = "log_deploy_state",notNull = true)
    private int logDeployState;

    //运行状态（30 ：成功  3：失败   其他）
    @Column(name = "log_run_status",notNull = true)
    private int logRunStatus = getLogCodeState() + getLogPackState()+ getLogCodeState();


    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogAddress() {
        return logAddress;
    }

    public void setLogAddress(String logAddress) {
        this.logAddress = logAddress;
    }

    public String getLogCodeTime() {
        return logCodeTime;
    }

    public void setLogCodeTime(String logCodeTime) {
        this.logCodeTime = logCodeTime;
    }

    public int getLogCodeState() {
        return logCodeState;
    }

    public void setLogCodeState(int logCodeState) {
        this.logCodeState = logCodeState;
    }

    public String getLogPackTime() {
        return logPackTime;
    }

    public void setLogPackTime(String logPackTime) {
        this.logPackTime = logPackTime;
    }

    public int getLogPackState() {
        return logPackState;
    }

    public void setLogPackState(int logPackState) {
        this.logPackState = logPackState;
    }

    public String getLogDeployTime() {
        return logDeployTime;
    }

    public void setLogDeployTime(String logDeployTime) {
        this.logDeployTime = logDeployTime;
    }

    public int getLogDeployState() {
        return logDeployState;
    }

    public void setLogDeployState(int logDeployState) {
        this.logDeployState = logDeployState;
    }

    public int getLogRunStatus() {
        return logRunStatus;
    }

    public void setLogRunStatus(int logRunStatus) {
        this.logRunStatus = logRunStatus;
    }
}
