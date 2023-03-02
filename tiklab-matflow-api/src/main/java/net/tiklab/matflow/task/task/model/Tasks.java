package net.tiklab.matflow.task.task.model;

import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
public class Tasks {

    @ApiProperty(name="taskId",desc="流水线id",eg="@selectOne")
    private String taskId;

    @ApiProperty(name="taskSort",desc="流水线id",eg="@selectOne")
    private int taskSort;

    @ApiProperty(name="taskType",desc="流水线id",eg="@selectOne")
    private int taskType;

    @ApiProperty(name="stages",desc="流水线id",eg="@selectOne")
    private int stages;

    @ApiProperty(name="stagesId",desc="流水线id",eg="@selectOne")
    private String stagesId;

    @ApiProperty(name="values",desc="流水线id",eg="@selectOne")
    private Object values;

    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.id",target = "pipelineId")
    })
    @JoinQuery(key = "id")
    private Pipeline pipeline;

    @ApiProperty(name="name",desc="流水线id",eg="@selectOne")
    private String name;

    public Tasks() {
    }

    public Tasks(String pipelineId) {
        this.pipeline = new Pipeline(pipelineId);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public Object getValues() {
        return values;
    }

    public void setValues(Object values) {
        this.values = values;
    }

    public int getStages() {
        return stages;
    }

    public void setStages(int stages) {
        this.stages = stages;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
