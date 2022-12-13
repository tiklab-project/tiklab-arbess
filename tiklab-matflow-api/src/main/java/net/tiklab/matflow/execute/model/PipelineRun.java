package net.tiklab.matflow.execute.model;

import java.util.List;

/**
 * 流水线运行过程的信息
 */
public class PipelineRun {

    private String createTime;

    private String execUser;

    private int runTime;

    private int runWay;

    private List<PipelineExecLog> runLogList;

    private List<PipelineRun> runList;

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getRunWay() {
        return runWay;
    }

    public void setRunWay(int runWay) {
        this.runWay = runWay;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExecUser() {
        return execUser;
    }

    public void setExecUser(String execUser) {
        this.execUser = execUser;
    }

    public List<PipelineRun> getRunList() {
        return runList;
    }

    public void setRunList(List<PipelineRun> runList) {
        this.runList = runList;
    }

    public List<PipelineExecLog> getRunLogList() {
        return runLogList;
    }

    public void setRunLogList(List<PipelineExecLog> runLogList) {
        this.runLogList = runLogList;
    }
}
