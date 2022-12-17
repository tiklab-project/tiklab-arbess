package net.tiklab.matflow.definition.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;


@ApiModel
@Join
@Mapper(targetAlias = "PipelineStagesTaskEntity")
public class PipelineStagesTask {

    @ApiProperty(name="configId",desc="配置id")
    private String configId;

    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    @ApiProperty(name="taskType",desc="类型")
    private int taskType;

    @ApiProperty(name="taskSort",desc="顺序")
    private int taskSort;

    @ApiProperty(name="stagesId",desc="阶段")
    private String stagesId;

    @ApiProperty(name="name",desc="名称")
    private String name;


    public PipelineStagesTask() {
    }

    public PipelineStagesTask(String createTime, int taskType, int taskSort, String stagesId) {
        this.createTime = createTime;
        this.taskType = taskType;
        this.taskSort = taskSort;
        this.stagesId = stagesId;
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

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
