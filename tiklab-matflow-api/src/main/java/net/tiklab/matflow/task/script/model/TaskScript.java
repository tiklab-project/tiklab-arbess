package net.tiklab.matflow.task.script.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "TaskScriptEntity")
public class TaskScript {

    @ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    @ApiProperty(name = "taskName",desc = "名称")
    private String taskName;

    @ApiProperty(name = "type",desc = "类型 71:bat脚本 72:shell脚本")
    private int type;

    @ApiProperty(name = "scriptOrder",desc = "命令")
    private String scriptOrder;

    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

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
















