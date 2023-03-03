package net.tiklab.matflow.stages.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

/**
 * 阶段运行实例模型
 */

@ApiModel
@Join
@Mapper(targetAlias = "StageInstanceEntity")
public class StageInstance {

    @ApiProperty(name = "id" ,desc = "id")
    private String id;

    @ApiProperty(name = "stageName" ,desc = "阶段名称")
    private String stageName;

    @ApiProperty(name = "instanceId" ,desc = "实例id")
    private String instanceId;

    @ApiProperty(name = "stageSort" ,desc = "阶段顺序")
    private int stageSort ;

    @ApiProperty(name = "stageAddress" ,desc = "运行日志地址")
    private String stageAddress;

    @ApiProperty(name = "stageTime" ,desc = "运行时间")
    private int stageTime;

    @ApiProperty(name = "stageState" ,desc = "运行状态")
    private int stageState;

    @ApiProperty(name = "stagesId" ,desc = "阶段id")
    private String stagesId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public int getStageSort() {
        return stageSort;
    }

    public void setStageSort(int stageSort) {
        this.stageSort = stageSort;
    }

    public String getStageAddress() {
        return stageAddress;
    }

    public void setStageAddress(String stageAddress) {
        this.stageAddress = stageAddress;
    }

    public int getStageTime() {
        return stageTime;
    }

    public void setStageTime(int stageTime) {
        this.stageTime = stageTime;
    }

    public int getStageState() {
        return stageState;
    }

    public void setStageState(int stageState) {
        this.stageState = stageState;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }
}
