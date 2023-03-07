package io.tiklab.matflow.pipeline.overview.model;


import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
import io.tiklab.user.user.model.User;

/**
 * 流水线概况统计模型
 */

@ApiModel
public class PipelineOverview {

    @ApiProperty(name="number",desc="运行次数")
    private int number;

    @ApiProperty(name="execTime",desc="平均执行时长")
    private int execTime;

    @ApiProperty(name="user",desc="执行人")
    private User user;

    @ApiProperty(name="successNumber",desc="成功次数")
    private int successNumber;

    @ApiProperty(name="errorNumber",desc="失败次数")
    private int errorNumber;

    @ApiProperty(name="removeNumber",desc="停止次数")
    private int removeNumber;

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

    public int getRemoveNumber() {
        return removeNumber;
    }

    public void setRemoveNumber(int removeNumber) {
        this.removeNumber = removeNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
