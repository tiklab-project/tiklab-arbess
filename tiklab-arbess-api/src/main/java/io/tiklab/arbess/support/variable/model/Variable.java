package io.tiklab.arbess.support.variable.model;

import io.tiklab.core.BaseModel;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;
import java.util.List;

/**
 * 流水线条件模型
 */
//@ApiModel
@Join
@Mapper
public class Variable extends BaseModel {


    //@ApiProperty(name="varId",desc="id")
    private String varId;

    //@ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    //@ApiProperty(name="varType",desc="类型 str.字符串 single .单选")
    private String varType;

    //@ApiProperty(name="type",desc="类型 1.全局 2.项目")
    private int type;

    //@ApiProperty(name="taskId",desc="任务id")
    private String taskId;

    //@ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    //@ApiProperty(name = "varKey",desc="名称")
    private String varKey;

    //@ApiProperty(name = "varValue",desc="默认值")
    private String varValue;

    //@ApiProperty(name = "varValues",desc="值")
    private String varValues;

    private List<String> valueList;

    public Variable() {
    }

    public Variable(String varKey, String varValue) {
        this.varKey = varKey;
        this.varValue = varValue;
    }

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

    public String getVarType() {
        return varType;
    }

    public Variable setVarType(String varType) {
        this.varType = varType;
        return this;
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

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
