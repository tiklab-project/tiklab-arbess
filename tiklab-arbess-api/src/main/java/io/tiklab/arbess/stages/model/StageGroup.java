package io.tiklab.arbess.stages.model;


import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.ArrayList;
import java.util.List;

public class StageGroup {

    //@ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    //@ApiProperty(name="stageSort",desc="阶段顺序")
    private int stageSort;

    // 阶段类型
    private String stageType;

    // 任务组
    private List<String> taskTypeList = new ArrayList<>();

    public String getStageType() {
        return stageType;
    }

    public void setStageType(String stageType) {
        this.stageType = stageType;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getStageSort() {
        return stageSort;
    }

    public void setStageSort(int stageSort) {
        this.stageSort = stageSort;
    }


    public List<String> getTaskTypeList() {
        return taskTypeList;
    }

    public void setTaskTypeList(List<String> taskTypeList) {
        this.taskTypeList = taskTypeList;
    }
}
