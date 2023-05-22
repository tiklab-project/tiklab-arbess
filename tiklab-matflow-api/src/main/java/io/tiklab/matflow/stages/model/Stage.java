package io.tiklab.matflow.stages.model;


import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

/**
 * 流水线阶段模型
 */
@ApiModel
@Join
@Mapper(targetAlias = "StageEntity")
public class Stage {

    @ApiProperty(name = "stageId",desc="id")
    private String stageId;

    @ApiProperty(name = "stageName",desc="名称")
    private String stageName;

    @ApiProperty(name = "createTime",desc="创建时间")
    private String createTime;

    @ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    @ApiProperty(name="stageSort",desc="阶段顺序")
    private int stageSort;

    @ApiProperty(name = "parentId",desc="主阶段")
    private String parentId;

    @ApiProperty(name = "code",desc="是否是源码")
    private boolean code;

    @ApiProperty(name = "taskValues",desc="阶段任务")
    private List<Tasks> taskValues;

    @ApiProperty(name = "stageList",desc="阶段")
    private List<Stage> stageList;

    @ApiProperty(name = "taskType",desc="任务类型")
    private String taskType;

    @ApiProperty(name = "taskId",desc="任务id")
    private String taskId;

    @ApiProperty(name = "values",desc="更新内容")
    private Object values;

    @ApiProperty(name = "taskSort",desc="任务顺序")
    private int taskSort;

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Object getValues() {
        return values;
    }

    public void setValues(Object values) {
        this.values = values;
    }

    public Stage() {
    }

    public List<Stage> getStageList() {
        return stageList;
    }

    public void setStageList(List<Stage> stageList) {
        this.stageList = stageList;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public int getStageSort() {
        return stageSort;
    }

    public void setStageSort(int stageSort) {
        this.stageSort = stageSort;
    }

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public Stage(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public List<Tasks> getTaskValues() {
        return taskValues;
    }

    public void setTaskValues(List<Tasks> taskValues) {
        this.taskValues = taskValues;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

}
