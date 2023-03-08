package io.tiklab.matflow.support.postprocess.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 任务实例实体
 */
@Entity
@Table(name="pip_postprocess_instance")
public class PostprocessInstanceEntity {


    @Id
    @GeneratorValue
    @Column(name = "id")
    private String id;

    //实例id
    @Column(name = "instance_id")
    private String instanceId;

    //任务实例id
    @Column(name = "taskInstance_id")
    private String taskInstanceId;

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

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }
}
