package io.thoughtware.matflow.support.variable.model;

import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.join.annotation.Join;

/**
 * 流水线条件模型
 */
//@ApiModel
public class ExecVariable {

    //@ApiProperty(name="varType",desc="类型 str.字符串 single.单选")
    private String varType;

    //@ApiProperty(name = "varKey",desc="名称")
    private String varKey;

    //@ApiProperty(name = "varValue",desc="默认值")
    private Object varValue;

    //@ApiProperty(name = "pipelineId",desc="默认值")
    private String pipelineId;


    public String getVarType() {
        return varType;
    }

    public ExecVariable setVarType(String varType) {
        this.varType = varType;
        return this;
    }

    public String getVarKey() {
        return varKey;
    }

    public ExecVariable setVarKey(String varKey) {
        this.varKey = varKey;
        return this;
    }

    public Object getVarValue() {
        return varValue;
    }

    public ExecVariable setVarValue(Object varValue) {
        this.varValue = varValue;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public ExecVariable setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }
}
