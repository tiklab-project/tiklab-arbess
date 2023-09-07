package io.tiklab.matflow.support.variable.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

/**
 * 流水线条件模型
 */
@ApiModel
public class ExecVariable {

    @ApiProperty(name="taskType",desc="类型 1.字符串 2.单选")
    private int taskType;

    @ApiProperty(name = "varKey",desc="名称")
    private String varKey;

    @ApiProperty(name = "varValue",desc="默认值")
    private Object varValue;

    @ApiProperty(name = "pipelineId",desc="默认值")
    private String pipelineId;


    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getVarKey() {
        return varKey;
    }

    public void setVarKey(String varKey) {
        this.varKey = varKey;
    }

    public Object getVarValue() {
        return varValue;
    }

    public void setVarValue(Object varValue) {
        this.varValue = varValue;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
