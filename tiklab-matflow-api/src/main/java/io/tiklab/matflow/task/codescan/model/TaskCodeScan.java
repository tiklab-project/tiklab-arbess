package io.tiklab.matflow.task.codescan.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;


/**
 * 任务代码扫描模型
 */
//@ApiModel
@Join
@Mapper
public class TaskCodeScan {

    //@ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //@ApiProperty(name="projectName",desc="项目名称")
    private String projectName;

    //授权id
    //@ApiProperty(name="authName",desc="授权id")
    private String authId;


    // 是否开启断言
    private Boolean openAssert;

    // 是否启用调试模式
    private Boolean openDebug;

    //授权信息
    private Object auth;

    private int sort;

    private String type;


    public Boolean getOpenAssert() {
        return openAssert;
    }

    public TaskCodeScan setOpenAssert(Boolean openAssert) {
        this.openAssert = openAssert;
        return this;
    }

    public Boolean getOpenDebug() {
        return openDebug;
    }

    public TaskCodeScan setOpenDebug(Boolean openDebug) {
        this.openDebug = openDebug;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }
}
