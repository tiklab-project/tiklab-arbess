package io.tiklab.matflow.task.codescan.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
/**
 * 任务代码扫描模型
 */
@ApiModel
@Join
@Mapper(targetAlias = "TaskCodeScanEntity")
public class TaskCodeScan {

    @ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    @ApiProperty(name="projectName",desc="项目名称")
    private String projectName;

    //授权id
    @ApiProperty(name="authName",desc="授权id")
    private String authId;

    //授权信息
    private Object auth;

    private int sort;

    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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
