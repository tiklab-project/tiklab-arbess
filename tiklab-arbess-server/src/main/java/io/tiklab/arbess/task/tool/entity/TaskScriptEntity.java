package io.tiklab.arbess.task.tool.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_script")
public class TaskScriptEntity {

    //id
    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "script_order")
    private String scriptOrder;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public TaskScriptEntity setType(String type) {
        this.type = type;
        return this;
    }

    public String getScriptOrder() {
        return scriptOrder;
    }

    public void setScriptOrder(String scriptOrder) {
        this.scriptOrder = scriptOrder;
    }

}
