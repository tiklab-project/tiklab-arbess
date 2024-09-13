package io.thoughtware.arbess.support.trigger.model;

import io.thoughtware.arbess.pipeline.definition.model.Pipeline;
import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;



/**
 * 流水线触发器模型
 */
//@ApiModel
@Join
@Mapper
public class Trigger {

    //@ApiProperty(name = "triggerId",desc="id")
    private String triggerId;

    //@ApiProperty(name = "name",desc="名称")
    private String name;

    //@ApiProperty(name = "taskType",desc="类型 81:定时任务")
    private int taskType;

    //@ApiProperty(name = "createTime",desc="创建时间")
    private String createTime;

    //流水线
    //@ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.id",target = "pipelineId")
    })
    @JoinQuery(key = "id")
    private Pipeline pipeline;


    //@ApiProperty(name="taskSort",desc="顺序")
    private int taskSort;

    //@ApiProperty(name="values",desc="更改的数据")
    private Object values;

    // 状态 1--未执行 2--已执行
    private String state;

    public String getState() {
        return state;
    }

    public Trigger setState(String state) {
        this.state = state;
        return this;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public Object getValues() {
        return values;
    }

    public void setValues(Object values) {
        this.values = values;
    }
}
