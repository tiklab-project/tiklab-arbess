package net.tiklab.matflow.execute.model;


import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.user.user.model.User;

/**
 * 流水线概况统计
 */

@ApiModel
public class PipelineOverview {

    //运行次数
    private int number;

    //平均执行时长
    private int execTime;

    //执行人
    private User user;

    //成功次数
    private int successNumber;

    //失败次数
    private int errorNumber;

    //停止次数
    private int removeNumber;

    //时间
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
