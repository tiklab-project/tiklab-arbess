package io.tiklab.arbess.support.condition.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;


/**
 * 流水线变量模型
 */
//@ApiModel
@Join
@Mapper
public class Condition {

    //@ApiProperty(name="condId",desc="条件id")
    private String condId;

    //@ApiProperty(name="condName",desc="条件名称")
    private String condName;

    //@ApiProperty(name="taskId",desc="任务id")
    private String taskId;

    //@ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    //@ApiProperty(name="condType",desc="条件类型 1.等于 2.不等于")
    private int condType;

    //@ApiProperty(name="condKey",desc="条件key")
    private String condKey;

    //@ApiProperty(name="condValue",desc="条件value")
    private String condValue;


    public String getCondId() {
        return condId;
    }

    public void setCondId(String condId) {
        this.condId = condId;
    }

    public String getCondName() {
        return condName;
    }

    public void setCondName(String condName) {
        this.condName = condName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCondType() {
        return condType;
    }

    public void setCondType(int condType) {
        this.condType = condType;
    }

    public String getCondKey() {
        return condKey;
    }

    public void setCondKey(String condKey) {
        this.condKey = condKey;
    }

    public String getCondValue() {
        return condValue;
    }

    public void setCondValue(String condValue) {
        this.condValue = condValue;
    }
}
