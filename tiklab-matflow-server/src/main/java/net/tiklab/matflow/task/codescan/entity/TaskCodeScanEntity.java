package net.tiklab.matflow.task.codescan.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_code_scan")
public class TaskCodeScanEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    @Column(name = "task_name" ,notNull = true)
    private String taskName;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "auth_id")
    private String authId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
