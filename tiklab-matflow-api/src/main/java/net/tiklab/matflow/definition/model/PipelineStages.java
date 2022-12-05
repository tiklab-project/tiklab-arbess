package net.tiklab.matflow.definition.model;

import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiProperty;

public class PipelineStages {

    @ApiProperty(name = "configId",desc="id")
    private String configId;

    @ApiProperty(name = "name",desc="名称")
    private String name;

    @ApiProperty(name = "createTime",desc="创建时间")
    private String createTime;

    //流水线
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    @ApiProperty(name="taskSort",desc="顺序")
    private int taskSort;

    @ApiProperty(name = "taskType",desc="类型")
    private int taskType;

    @ApiProperty(name = "taskStage",desc="阶段")
    private int taskStage;

    //更改的数据
    private Object values;


    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getTaskStage() {
        return taskStage;
    }

    public void setTaskStage(int taskStage) {
        this.taskStage = taskStage;
    }

    public Object getValues() {
        return values;
    }

    public void setValues(Object values) {
        this.values = values;
    }
}
