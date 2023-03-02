package net.tiklab.matflow.task.task.entity;


import net.tiklab.dal.jpa.annotation.*;

/**
 * 流水线顺序配置实体
 */

@Entity
@Table(name="pip_tasks")
public class TasksEntity {

    //流水线配置id
    @Id
    @GeneratorValue
    @Column(name = "task_id",notNull = true)
    private String taskId;

    //创建配置时间
    @Column(name = "create_time",notNull = true)
    private String createTime ;

    //流水线
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    @Column(name = "stages_id",notNull = true)
    private String stagesId;

    //源码类型
    @Column(name = "task_type",notNull = true)
    private int taskType;

    //顺序
    @Column(name = "task_sort",notNull = true)
    private int taskSort;


    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
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

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }


}
