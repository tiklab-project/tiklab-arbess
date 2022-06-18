package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.join.annotation.Join;

/**
 * 流水线近期构建状态
 */

@ApiModel
@Join
public class PipelineExecState {

    private String time;

    private int successNumber;

    private int errorNumber;

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
