package net.tiklab.matflow.stages.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_stage_instance")
public class StageInstanceEntity {

    //日志id
    @Id
    @GeneratorValue
    @Column(name = "id")
    private String id;

    //任务名称
    @Column(name = "stage_name")
    private String stageName;

    //实例id
    @Column(name = "instance_id")
    private String instanceId;

    //顺序
    @Column(name = "stage_sort")
    private int stageSort ;

    //运行日志
    @Column(name = "stage_address")
    private String stageAddress;

    //运行时间
    @Column(name = "stage_time")
    private int stageTime;

    //运行状态
    @Column(name = "stage_state")
    private int stageState;

    //阶段id
    @Column(name = "stages_id")
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
