package com.tiklab.matfiow.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.tiklab.matfiow.definition.model.Pipeline;
import com.doublekit.user.user.model.User;

/**
 * 流水线历史
 */

@ApiModel
@Join
@Mapper(targetAlias = "PipelineExecHistoryEntity")
public class PipelineExecHistory {

    //构建历史id
    @ApiProperty(name="historyId",desc="构建历史id")
    private String historyId;

    //创建时间
    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    //构建方式
    @ApiProperty(name="runWay",desc="运行方式")
    private int runWay;

    //运行日志
    @ApiProperty(name="runLog",desc="运行日志")
    private String runLog;

    //执行人
    @ApiProperty(name="user",desc="认证配置",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    //状态
    @ApiProperty(name="runStatus",desc="运行状态")
    private int runStatus;

    //执行时间
    @ApiProperty(name="runTime",desc="运行时间")
    private int runTime;

   //流水线id
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    //执行位置
    @ApiProperty(name="sort",desc="执行过程位置")
    private int sort;

    //执行状态
    @ApiProperty(name="status",desc="执行过程状态")
    private int status;

    //构建次数
    @ApiProperty(name="findNumber",desc="构建次数")
    private int findNumber;

    //运行状态（1.运行完成，0.运行中）
    @ApiProperty(name="findState",desc="运行状态（1.运行完成，0.运行中）")
    private int findState;

    //运行时间（转换为天，月，日 ，时，分，秒格式）
    @ApiProperty(name="execTime",desc="运行时间（转换为天，月，日 ，时，分，秒格式）")
    private String execTime;

    //时间
    @ApiProperty(name="oneTime",desc="第一阶段时间")
    private String oneTime;

    @ApiProperty(name="twoTime",desc="第二阶段时间")
    private String twoTime;

    @ApiProperty(name="threeTime",desc="第三阶段时间")
    private String threeTime;

    @ApiProperty(name="fourTime",desc="第四阶段时间")
    private String fourTime;

    @ApiProperty(name="allTime",desc="总时间")
    private String allTime;


    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
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

    public String getExecTime() {
        execTime = formatDateTime( runTime+2);
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getFindNumber() {
        return findNumber;
    }

    public void setFindNumber(int findNumber) {
        this.findNumber = findNumber;
    }

    public String getOneTime() {
        return oneTime;
    }

    public void setOneTime(String oneTime) {
        this.oneTime = oneTime;
    }

    public String getTwoTime() {
        return twoTime;
    }

    public void setTwoTime(String twoTime) {
        this.twoTime = twoTime;
    }

    public String getThreeTime() {
        return threeTime;
    }

    public void setThreeTime(String threeTime) {
        this.threeTime = threeTime;
    }

    public String getFourTime() {
        return fourTime;
    }

    public void setFourTime(String fourTime) {
        this.fourTime = fourTime;
    }

    public String getAllTime() {
        return allTime;
    }

    public void setAllTime(String allTime) {
        this.allTime = allTime;
    }

    public int getFindState() {
        return findState;
    }

    public void setFindState(int findState) {
        this.findState = findState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String formatDateTime(long time) {
        String DateTimes ;
        long days = time / ( 60 * 60 * 24);
        long hours = (time % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (time % ( 60 * 60)) /60;
        long seconds = time % 60;
        if(days>0){
            DateTimes= days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
        }else if(hours>0){
            DateTimes=hours + "小时" + minutes + "分钟" + seconds + "秒";
        }else if(minutes>0){
            DateTimes=minutes + "分钟" + seconds + "秒";
        }else{
            DateTimes=seconds + "秒";
        }
        return DateTimes;
    }
}
