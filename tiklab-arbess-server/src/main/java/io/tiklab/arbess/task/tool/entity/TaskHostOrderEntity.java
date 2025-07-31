package io.tiklab.arbess.task.tool.entity;

import io.tiklab.dal.jpa.annotation.Column;
import io.tiklab.dal.jpa.annotation.Entity;
import io.tiklab.dal.jpa.annotation.Id;
import io.tiklab.dal.jpa.annotation.Table;

@Entity
@Table(name="pip_task_host_order")
public class TaskHostOrderEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    @Column(name = "auth_id")
    private String authId;

    @Column(name = "run_order")
    private String order;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
