package io.tiklab.matflow.task.script.entity;

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
    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getScriptOrder() {
        return scriptOrder;
    }

    public void setScriptOrder(String scriptOrder) {
        this.scriptOrder = scriptOrder;
    }

}
