package io.thoughtware.arbess.task.task.entity;

import io.thoughtware.dal.jpa.annotation.*;

/**
 * 流水线日志
 */

@Entity
@Table(name="pip_task_instance")
public class TaskInstanceEntity {

    //日志id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "instance_id")
    private String instanceId;

    //运行类型
    @Column(name = "task_type")
    private String taskType ;

    @Column(name = "task_sort")
    private int taskSort ;

    //运行日志
    @Column(name = "log_address")
    private String logAddress;

    //运行时间
    @Column(name = "run_time")
    private int runTime;

    //运行状态
    @Column(name = "run_state")
    private String runState;

    //阶段id
    @Column(name = "stages_id")
    private String stagesId;

    //任务名称
    @Column(name = "task_name")
    private String taskName;

    @Column(name = "postprocess_id")
    private String postprocessId;


    public String getPostprocessId() {
        return postprocessId;
    }

    public void setPostprocessId(String postprocessId) {
        this.postprocessId = postprocessId;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public String getLogAddress() {
        return logAddress;
    }

    public void setLogAddress(String logAddress) {
        this.logAddress = logAddress;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getRunState() {
        return runState;
    }

    public void setRunState(String runState) {
        this.runState = runState;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
