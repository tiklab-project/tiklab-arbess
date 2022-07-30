package com.tiklab.matflow.definition.model;


import com.tiklab.join.annotation.Join;
import com.tiklab.postin.annotation.ApiModel;
import com.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
public class MatFlowStatus {

    //id
    @ApiProperty(name="matFlowId",desc="流水线id")
    private String matFlowId;

    //收藏状态
    @ApiProperty(name="matFlowCollect",desc="收藏状态",required = true)
    private int matFlowCollect;

    //构建状态
    @ApiProperty(name="logCreateTime",desc="构建状态")
    private int structureStatus;

    //任务名
    @ApiProperty(name="matFlowName",desc="任务名")
    private String matFlowName;

    //上次成功时间
    @ApiProperty(name="listStructureTime",desc="上次成功时间")
    private String lastStructureTime;

    //上次构建时间
    @ApiProperty(name="listSuccessStatus",desc="上次构建时间")
    private String lastSuccessTime;

    //运行状态
    @ApiProperty(name="matFlowCollect",desc="运行状态",required = true)
    private int matFlowState;


    public String getMatFlowId() {
        return matFlowId;
    }

    public void setMatFlowId(String matFlowId) {
        this.matFlowId = matFlowId;
    }

    public int getStructureStatus() {
        return structureStatus;
    }

    public void setStructureStatus(int structureStatus) {
        this.structureStatus = structureStatus;
    }

    public String getMatFlowName() {
        return matFlowName;
    }

    public void setMatFlowName(String matFlowName) {
        this.matFlowName = matFlowName;
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

    public int getMatFlowCollect() {
        return matFlowCollect;
    }

    public void setMatFlowCollect(int matFlowCollect) {
        this.matFlowCollect = matFlowCollect;
    }

    public int getMatFlowState() {
        return matFlowState;
    }

    public void setMatFlowState(int matFlowState) {
        this.matFlowState = matFlowState;
    }
}
