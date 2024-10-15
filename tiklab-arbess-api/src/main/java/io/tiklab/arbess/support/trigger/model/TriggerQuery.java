package io.tiklab.arbess.support.trigger.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;


/**
 * 流水线触发器模型
 */
//@ApiModel
@Join
@Mapper
public class TriggerQuery {


    //@ApiProperty(name = "name",desc="名称")
    private String name;

    //@ApiProperty(name = "taskType",desc="类型 81:定时任务")
    private int taskType;

    private String pipelineId;

    // 状态 1--未执行 2--已执行
    private String state;


    public String getName() {
        return name;
    }

    public TriggerQuery setName(String name) {
        this.name = name;
        return this;
    }

    public int getTaskType() {
        return taskType;
    }

    public TriggerQuery setTaskType(int taskType) {
        this.taskType = taskType;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public TriggerQuery setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public String getState() {
        return state;
    }

    public TriggerQuery setState(String state) {
        this.state = state;
        return this;
    }
}
