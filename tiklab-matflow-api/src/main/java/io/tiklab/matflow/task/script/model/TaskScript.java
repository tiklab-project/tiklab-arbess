package io.tiklab.matflow.task.script.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;


/**
 * 任务脚本模型
 */
//@ApiModel
@Join
@Mapper
public class TaskScript {

    //@ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //@ApiProperty(name = "type",desc = "类型 bat:bat脚本 shell:shell脚本")
    private String type;

    //@ApiProperty(name = "scriptOrder",desc = "命令")
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScriptOrder() {
        return scriptOrder;
    }

    public void setScriptOrder(String scriptOrder) {
        this.scriptOrder = scriptOrder;
    }


}
















