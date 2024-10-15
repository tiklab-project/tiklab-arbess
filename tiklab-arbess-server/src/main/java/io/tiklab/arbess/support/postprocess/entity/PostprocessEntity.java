package io.tiklab.arbess.support.postprocess.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_postprocess")
public class PostprocessEntity {

    //id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "post_id")
    private String postId;

    @Column(name = "post_name")
    private String postName;

    @Column(name = "task_sort")
    private int taskSort;

    @Column(name = "task_type")
    private String taskType;

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

    public String getPostId() {
        return postId;
    }

    public PostprocessEntity setPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public String getPostName() {
        return postName;
    }

    public PostprocessEntity setPostName(String postName) {
        this.postName = postName;
        return this;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
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
