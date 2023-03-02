package net.tiklab.matflow.task.build.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_build")
public class TaskBuildEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    @Column(name = "task_name" ,notNull = true)
    private String taskName;

    //地址
    @Column(name = "build_address",notNull = true)
    private String buildAddress;

    //分支
    @Column(name = "build_order",notNull = true)
    private String buildOrder;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBuildAddress() {
        return buildAddress;
    }

    public void setBuildAddress(String buildAddress) {
        this.buildAddress = buildAddress;
    }

    public String getBuildOrder() {
        return buildOrder;
    }

    public void setBuildOrder(String buildOrder) {
        this.buildOrder = buildOrder;
    }

}
