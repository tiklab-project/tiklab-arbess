package net.tiklab.matflow.orther.model;

/**
 * 待办
 */
public class PipelineTask {

    //待办名称
    private String taskName;

    //创建时间
    private String createTime;

    //结束时间
    private String endTime;

    //创建人
    private String createUser;

    //执行人
    private String execUser;

    // 1.进行中  2.完成  3. 逾期
    private int state;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getExecUser() {
        return execUser;
    }

    public void setExecUser(String execUser) {
        this.execUser = execUser;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
