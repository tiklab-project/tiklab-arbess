package com.tiklab.matflow.instance.model;


import com.tiklab.postin.annotation.ApiModel;
import com.tiklab.postin.annotation.ApiProperty;

/**
 * 流水线近期构建状态
 */

@ApiModel
public class MatFlowExecState {

    @ApiProperty(name="time",desc="时间")
    private String time;

    @ApiProperty(name="successNumber",desc="成功次数")
    private int successNumber;

    @ApiProperty(name="errorNumber",desc="失败次数")
    private int errorNumber;

    @ApiProperty(name="removeNumber",desc="停止次数")
    private int removeNumber;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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
}
