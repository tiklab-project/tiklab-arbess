package io.tiklab.matflow.task.task.entity;


import io.tiklab.dal.jpa.annotation.*;

/**
 * 流水线顺序配置实体
 */

@Entity
@Table(name="pip_task")
public class TasksEntity {

    //流水线配置id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "task_id",notNull = true)
    private String taskId;

    //创建配置时间
    @Column(name = "create_time",notNull = true)
    private String createTime ;

    //流水线
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    @Column(name = "stage_id",notNull = true)
    private String stageId;

    @Column(name = "postprocess_id",notNull = true)
    private String postprocessId;

    @Column(name = "task_name",notNull = true)
    private String taskName;

    //源码类型
    @Column(name = "task_type",notNull = true)
    private String taskType;

    //顺序
    @Column(name = "task_sort",notNull = true)
    private int taskSort;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getPostprocessId() {
        return postprocessId;
    }

    public void setPostprocessId(String postprocessId) {
        this.postprocessId = postprocessId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
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


}
