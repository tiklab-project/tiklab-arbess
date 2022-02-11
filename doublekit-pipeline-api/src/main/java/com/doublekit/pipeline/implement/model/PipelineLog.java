package com.doublekit.pipeline.implement.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.join.annotation.Join;

import java.text.SimpleDateFormat;
import java.util.Date;

@ApiModel
@Join
public class PipelineLog {

    //日志id
    @ApiProperty(name="logId",desc="日志id")
    private String logId;

    //创建时间
    @ApiProperty(name="logCreateTime",desc="创建时间")
    private String logCreateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());;

    //日志地址
    @ApiProperty(name="logAddress",desc="日志地址")
    private String logAddress;

    //拉取时间
    @ApiProperty(name="logCodeTime",desc="拉取时间")
    private String logCodeTime;

    //拉取状态
    @ApiProperty(name="logCodeState",desc="拉取状态")
    private int  logCodeState;

    //打包时间
    @ApiProperty(name="logPackTime",desc="打包时间")
    private String logPackTime;

    //打包状态
    @ApiProperty(name="logPackState",desc="打包状态")
    private int  logPackState;

    //部署时间
    @ApiProperty(name="logDeployTime",desc="部署时间")
    private String logDeployTime;

    //部署状态
    @ApiProperty(name="logDeployState",desc="部署状态")
    private int logDeployState;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogCreateTime() {
        return logCreateTime;
    }

    public void setLogCreateTime(String logCreateTime) {
        this.logCreateTime = logCreateTime;
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
}
