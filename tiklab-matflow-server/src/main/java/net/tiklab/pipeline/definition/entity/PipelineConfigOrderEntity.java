package net.tiklab.pipeline.definition.entity;


import net.tiklab.dal.jpa.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 流水线顺序配置
 */

@Entity
@Table(name="pipeline_config_order")
public class PipelineConfigOrderEntity {

    //流水线配置id
    @Id
    @GeneratorValue
    @Column(name = "config_id",notNull = true)
    private String configId;

    //创建配置时间
    @Column(name = "create_time",notNull = true)
    private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    //流水线
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //源码
    @Column(name = "task_id",notNull = true)
    private String taskId;

    //源码类型
    @Column(name = "task_type",notNull = true)
    private int taskType;

    //顺序
    @Column(name = "task_sort",notNull = true)
    private int taskSort;

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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
