package io.tiklab.matflow.support.postprocess.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_postprocess")
public class PostprocessEntity {

    //id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "postprocess_id")
    private String postprocessId;

    @Column(name = "name")
    private String name;

    @Column(name = "task_sort")
    private int taskSort;

    @Column(name = "task_type")
    private int taskType;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "pipeline_id")
    private String pipelineId;

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getPostprocessId() {
        return postprocessId;
    }

    public void setPostprocessId(String postprocessId) {
        this.postprocessId = postprocessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
