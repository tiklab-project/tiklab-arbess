package com.doublekit.pipeline.definition.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
public class PipelineStatus {

    //id
    @ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    //流水线状态
    @ApiProperty(name="pipelineCollect",desc="流水线状态",required = true)
    private int pipelineCollect;

    //构建状态
    @ApiProperty(name="logCreateTime",desc="构建状态")
    private int structureStatus;

    //任务名
    @ApiProperty(name="pipelineName",desc="任务名")
    private String pipelineName;

    //上次成功时间
    @ApiProperty(name="listStructureTime",desc="上次成功时间")
    private String lastStructureTime;

    //上次构建时间
    @ApiProperty(name="listSuccessStatus",desc="上次构建时间")
    private String lastSuccessTime;


    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getStructureStatus() {
        return structureStatus;
    }

    public void setStructureStatus(int structureStatus) {
        this.structureStatus = structureStatus;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public String getLastStructureTime() {
        return lastStructureTime;
    }

    public void setLastStructureTime(String lastStructureTime) {
        this.lastStructureTime = lastStructureTime;
    }

    public String getLastSuccessTime() {
        return lastSuccessTime;
    }

    public void setLastSuccessTime(String lastSuccessTime) {
        this.lastSuccessTime = lastSuccessTime;
    }

    public int getPipelineCollect() {
        return pipelineCollect;
    }

    public void setPipelineCollect(int pipelineCollect) {
        this.pipelineCollect = pipelineCollect;
    }
}
