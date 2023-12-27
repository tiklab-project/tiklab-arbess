package io.thoughtware.matflow.stages.model;


import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.matflow.task.task.model.TaskInstance;



import java.util.List;

/**
 * 阶段运行实例模型
 */

//@ApiModel
@Join
@Mapper(targetAlias = "StageInstanceEntity")
public class StageInstance {

    //@ApiProperty(name = "id" ,desc = "id")
    private String id;

    //@ApiProperty(name = "stageName" ,desc = "阶段名称")
    private String stageName;

    //@ApiProperty(name = "instanceId" ,desc = "实例id")
    private String instanceId;

    //@ApiProperty(name = "stageSort" ,desc = "阶段顺序")
    private int stageSort ;

    //@ApiProperty(name = "stageAddress" ,desc = "运行日志地址")
    private String stageAddress;

    //@ApiProperty(name = "stageTime" ,desc = "运行时间")
    private int stageTime;

    //@ApiProperty(name = "stageState" ,desc = "运行状态")
    private String stageState;

    //@ApiProperty(name = "parentId" ,desc = "阶段id")
    private String parentId;

    //@ApiProperty(name = "stageRunLog" ,desc = "阶段运行日志")
    private String runLog;

    //@ApiProperty(name = "stageInstanceList" ,desc = "阶段实例")
    private List<StageInstance> stageInstanceList ;

    //@ApiProperty(name = "taskInstanceList" ,desc = "任务实例")
    private List<TaskInstance> taskInstanceList;


    public String getRunLog() {
        return runLog;
    }

    public void setRunLog(String runLog) {
        this.runLog = runLog;
    }

    public List<StageInstance> getStageInstanceList() {
        return stageInstanceList;
    }

    public void setStageInstanceList(List<StageInstance> stageInstanceList) {
        this.stageInstanceList = stageInstanceList;
    }

    public List<TaskInstance> getTaskInstanceList() {
        return taskInstanceList;
    }

    public void setTaskInstanceList(List<TaskInstance> taskInstanceList) {
        this.taskInstanceList = taskInstanceList;
    }

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

    public String getStageState() {
        return stageState;
    }

    public void setStageState(String stageState) {
        this.stageState = stageState;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
