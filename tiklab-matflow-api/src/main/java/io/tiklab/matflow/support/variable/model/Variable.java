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
@Join
@Mapper
public class Variable {


    @ApiProperty(name="varId",desc="id")
    private String varId;

    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    @ApiProperty(name="taskType",desc="类型 1.字符串 2.单选")
    private int taskType;

    @ApiProperty(name="type",desc="类型 1.全局 2.项目")
    private int type;

    @ApiProperty(name="taskId",desc="任务id")
    private String taskId;

    @ApiProperty(name = "varKey",desc="名称")
    private String varKey;

    @ApiProperty(name = "varValue",desc="默认值")
    private String varValue;

    @ApiProperty(name = "varValues",desc="值")
    private String varValues;

    private List<String> valueList;


    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    public String getVarId() {
        return varId;
    }

    public void setVarId(String varId) {
        this.varId = varId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getVarKey() {
        return varKey;
    }

    public void setVarKey(String varKey) {
        this.varKey = varKey;
    }

    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }

    public String getVarValues() {
        return varValues;
    }

    public void setVarValues(String varValues) {
        this.varValues = varValues;
    }
}
