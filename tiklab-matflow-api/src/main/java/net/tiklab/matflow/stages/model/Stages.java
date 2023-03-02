package net.tiklab.matflow.stages.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import java.util.List;

/**
 * 流水线阶段模型
 */
@ApiModel
@Join
@Mapper(targetAlias = "StagesEntity")
public class Stages {

    @ApiProperty(name = "stagesId",desc="id")
    private String stagesId;

    @ApiProperty(name = "stagesName",desc="名称")
    private String stagesName;

    @ApiProperty(name = "createTime",desc="创建时间")
    private String createTime;

    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.id",target = "pipelineId")
    })
    @JoinQuery(key = "id")
    private Pipeline pipeline;

    @ApiProperty(name="stagesSort",desc="阶段顺序")
    private int stagesSort;

    @ApiProperty(name = "stageId",desc="主阶段")
    private String stageId;

    @ApiProperty(name = "code",desc="是否是源码")
    private boolean code;

    @ApiProperty(name = "taskValues",desc="阶段任务")
    private List<Object> taskValues;

    @ApiProperty(name = "stagesList",desc="阶段")
    private List<Stages> stagesList;

    public Stages() {
    }

    public int getStagesSort() {
        return stagesSort;
    }

    public void setStagesSort(int stagesSort) {
        this.stagesSort = stagesSort;
    }

    public String getStagesName() {
        return stagesName;
    }

    public void setStagesName(String stagesName) {
        this.stagesName = stagesName;
    }


    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public Stages(String createTime) {
        this.createTime = createTime;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
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

    public List<Object> getTaskValues() {
        return taskValues;
    }

    public void setTaskValues(List<Object> taskValues) {
        this.taskValues = taskValues;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public List<Stages> getStagesList() {
        return stagesList;
    }

    public void setStagesList(List<Stages> stagesList) {
        this.stagesList = stagesList;
    }
}
