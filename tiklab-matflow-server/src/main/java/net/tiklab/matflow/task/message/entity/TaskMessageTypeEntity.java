package net.tiklab.matflow.task.message.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_message_type")
public class TaskMessageTypeEntity {

    @Id
    @GeneratorValue
    @Column(name = "id" ,notNull = true)
    private String id;

    //任务id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    //消息类型
    @Column(name = "task_type")
    private String taskType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


}
