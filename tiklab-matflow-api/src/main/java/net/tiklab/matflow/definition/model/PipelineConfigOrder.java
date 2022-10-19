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
@Mapper(targetAlias = "PipelineConfigOrderEntity")
public class PipelineConfigOrder {

    @ApiProperty(name="configId",desc="配置id")
    private String configId;

    ////流水线id
    //@ApiProperty(name="pipelineId",desc="流水线id")
    //private String pipelineId;

    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    @ApiProperty(name="taskId",desc="关联id")
    private String taskId;

    @ApiProperty(name="taskType",desc="类型")
    private int taskType;

    @ApiProperty(name="taskSort",desc="顺序")
    private int taskSort;

    //流水线
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    private PipelineCode pipelineCode;

    private PipelineTest pipelineTest;

    private PipelineBuild pipelineBuild;

    private PipelineDeploy pipelineDeploy;

    //信息
    private String message;

    //更改顺序的类型
    private int sort;

    public PipelineConfigOrder() {
    }

    public PipelineConfigOrder(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public PipelineConfigOrder(Pipeline pipeline, String message) {
        this.pipeline = pipeline;
        this.message = message;
    }

    public PipelineConfigOrder(int taskType, int taskSort, Pipeline pipeline) {
        this.taskType = taskType;
        this.taskSort = taskSort;
        this.pipeline = pipeline;
    }

    //public PipelineConfigOrder(String pipelineId) {
    //    this.pipelineId = pipelineId;
    //}

    //public PipelineConfigOrder(String pipelineId,String message) {
    //    this.pipelineId = pipelineId;
    //    this.message = message;
    //}

    //public PipelineConfigOrder(String pipelineId, int taskType, int taskSort) {
    //    this.pipelineId = pipelineId;
    //    this.taskType = taskType;
    //    this.taskSort = taskSort;
    //}

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    //public String getPipelineId() {
    //    return pipelineId;
    //}
    //
    //public void setPipelineId(String pipelineId) {
    //    this.pipelineId = pipelineId;
    //}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PipelineCode getPipelineCode() {
        return pipelineCode;
    }

    public void setPipelineCode(PipelineCode pipelineCode) {
        this.pipelineCode = pipelineCode;
    }

    public PipelineTest getPipelineTest() {
        return pipelineTest;
    }

    public void setPipelineTest(PipelineTest pipelineTest) {
        this.pipelineTest = pipelineTest;
    }

    public PipelineBuild getPipelineBuild() {
        return pipelineBuild;
    }

    public void setPipelineBuild(PipelineBuild pipelineBuild) {
        this.pipelineBuild = pipelineBuild;
    }

    public PipelineDeploy getPipelineDeploy() {
        return pipelineDeploy;
    }

    public void setPipelineDeploy(PipelineDeploy pipelineDeploy) {
        this.pipelineDeploy = pipelineDeploy;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }
}
