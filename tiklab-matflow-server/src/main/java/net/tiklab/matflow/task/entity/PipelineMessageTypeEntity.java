package net.tiklab.matflow.task.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_pipeline_task_message_type")
public class PipelineMessageTypeEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "message_task_id")
    private String messageTaskId;

    //消息类型
    @Column(name = "task_type")
    private String taskType;

    @Column(name = "config_id")
    private String configId;




    public String getMessageTaskId() {
        return messageTaskId;
    }

    public void setMessageTaskId(String messageTaskId) {
        this.messageTaskId = messageTaskId;
    }


    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}
