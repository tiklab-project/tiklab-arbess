package io.tiklab.arbess.task.tool.entity;

import io.tiklab.dal.jpa.annotation.Column;
import io.tiklab.dal.jpa.annotation.Entity;
import io.tiklab.dal.jpa.annotation.Id;
import io.tiklab.dal.jpa.annotation.Table;

@Entity
@Table(name="pip_task_check_point")
public class TaskCheckPointEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    // 等待时间
    @Column(name = "wail_time" ,notNull = true)
    private Integer wailTime;

    //审批id
    @Column(name = "inspect_ids" ,notNull = true)
    private String inspectIds;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getWailTime() {
        return wailTime;
    }

    public void setWailTime(Integer wailTime) {
        this.wailTime = wailTime;
    }

    public String getInspectIds() {
        return inspectIds;
    }

    public void setInspectIds(String inspectIds) {
        this.inspectIds = inspectIds;
    }
}
