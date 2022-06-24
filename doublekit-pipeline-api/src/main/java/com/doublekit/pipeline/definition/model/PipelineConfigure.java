package com.doublekit.pipeline.definition.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;


@ApiModel
@Join
@Mapper(targetAlias = "PipelineConfigureEntity")
public class PipelineConfigure {

    //流水线配置id
    @ApiProperty(name="id",desc="配置id")
    private String configureId;

    //创建配置时间
    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    //流水线
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    //源码
    @ApiProperty(name = "taskId",desc = "源码")
    private String taskId;

    //源码类型
    @ApiProperty(name = "taskType",desc = "源码类型")
    private int taskType;

    //顺序
    @ApiProperty(name = "taskSort",desc = "顺序")
    private int taskSort;

    //别名
    @ApiProperty(name = "taskAlias",desc = "别名")
    private String taskAlias;

    @ApiProperty(name = "view",desc = "视图")
    private int view;

    @ApiProperty(name = "userId",desc = "用户Id")
    private String userId;


    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getTaskAlias() {
        return taskAlias;
    }

    public void setTaskAlias(String taskAlias) {
        this.taskAlias = taskAlias;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
