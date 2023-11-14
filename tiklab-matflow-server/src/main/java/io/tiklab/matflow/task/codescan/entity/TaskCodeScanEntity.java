package io.tiklab.matflow.task.codescan.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 代码扫描
 * @author zcamy
 */

@Entity
@Table(name="pip_task_code_scan")
public class TaskCodeScanEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "auth_id")
    private String authId;

    // 是否开启断言
    @Column(name = "open_assert")
    private String openAssert;

    // 是否启用调试模式
    @Column(name = "open_debug")
    private String openDebug;


    public String getOpenAssert() {
        return openAssert;
    }

    public TaskCodeScanEntity setOpenAssert(String openAssert) {
        this.openAssert = openAssert;
        return this;
    }

    public String getOpenDebug() {
        return openDebug;
    }

    public TaskCodeScanEntity setOpenDebug(String openDebug) {
        this.openDebug = openDebug;
        return this;
    }

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
