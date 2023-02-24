package net.tiklab.matflow.pipeline.definition.entity;


import net.tiklab.dal.jpa.annotation.*;

/**
 * 流水线顺序配置
 */

@Entity
@Table(name="pip_pipeline_tasks")
public class PipelineTasksEntity {

    //流水线配置id
    @Id
    @GeneratorValue
    @Column(name = "config_id",notNull = true)
    private String configId;

    //创建配置时间
    @Column(name = "create_time",notNull = true)
    private String createTime ;

    //流水线
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //源码类型
    @Column(name = "task_type",notNull = true)
    private int taskType;

    //顺序
    @Column(name = "task_sort",notNull = true)
    private int taskSort;

    @Column(name = "name",notNull = true)
    private String name;


    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
