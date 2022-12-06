package net.tiklab.matflow.definition.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

/**
 * 流水线配置顺序
 */

@ApiModel
@Join
@Mapper(targetAlias = "PipelineCourseConfigEntity")
public class PipelineCourseConfig {

    @ApiProperty(name="configId",desc="配置id")
    private String configId;

    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    @ApiProperty(name="taskType",desc="类型")
    private int taskType;

    @ApiProperty(name="taskSort",desc="顺序")
    private int taskSort;

    //更改的数据
    private Object values;

    //流水线
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    @ApiProperty(name="stagesId",desc="阶段")
    private String stagesId;


    public PipelineCourseConfig() {
    }

    public PipelineCourseConfig(String configId, int taskType, int taskSort, Object values, Pipeline pipeline) {
        this.configId = configId;
        this.taskType = taskType;
        this.taskSort = taskSort;
        this.values = values;
        this.pipeline = pipeline;
    }

    public PipelineCourseConfig(String pipelineId) {
        this.pipeline = new Pipeline(pipelineId);
    }

    public PipelineCourseConfig(int taskType, int taskSort, String pipelineId) {
        this.taskType = taskType;
        this.taskSort = taskSort;
        this.pipeline = new Pipeline(pipelineId);
    }

    public Object getValues() {
        return values;
    }

    public void setValues(Object values) {
        this.values = values;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }

}
