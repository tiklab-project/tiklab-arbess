package io.tiklab.arbess.support.variable.model;

/**
 * 流水线条件模型
 */
public class ExecVariable {

    // key
    private String varKey;

    // value
    private String varValue;

    public ExecVariable() {
    }

    public ExecVariable(String varKey, String varValue) {
        this.varKey = varKey;
        this.varValue = varValue;
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
}
