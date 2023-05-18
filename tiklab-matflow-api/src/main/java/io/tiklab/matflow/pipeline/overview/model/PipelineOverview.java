package io.tiklab.matflow.pipeline.overview.model;


import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

/**
 * 流水线概况统计模型
 */

@ApiModel
public class PipelineOverview {

    @ApiProperty(name="allNumber",desc="运行次数")
    private int allNumber;

    @ApiProperty(name="execTime",desc="平均执行时长")
    private int execTime;

    @ApiProperty(name="successNumber",desc="成功次数")
    private int successNumber;

    @ApiProperty(name="errorNumber",desc="失败次数")
    private int errorNumber;

    @ApiProperty(name="haltNumber",desc="停止次数")
    private int haltNumber;

    @ApiProperty(name="time",desc="平均执行时长(转换成时分秒)")
    private String time;


    public int getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(int successNumber) {
        this.successNumber = successNumber;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public int getHaltNumber() {
        return haltNumber;
    }

    public void setHaltNumber(int haltNumber) {
        this.haltNumber = haltNumber;
    }

    public int getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(int allNumber) {
        this.allNumber = allNumber;
    }

    public int getExecTime() {
        return execTime;
    }

    public void setExecTime(int execTime) {
        this.execTime = execTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
