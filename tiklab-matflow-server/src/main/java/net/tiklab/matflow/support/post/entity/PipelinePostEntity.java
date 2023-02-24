package net.tiklab.matflow.support.post.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_pipeline_post")
public class PipelinePostEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "config_id")
    private String configId;

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

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
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
