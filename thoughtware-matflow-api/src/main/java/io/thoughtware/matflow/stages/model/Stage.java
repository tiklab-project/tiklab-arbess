package io.thoughtware.matflow.stages.model;


import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.matflow.task.task.model.Tasks;



import java.util.List;

/**
 * 流水线阶段模型
 */
//@ApiModel
@Join
@Mapper
public class Stage {

    //@ApiProperty(name = "stageId",desc="id")
    private String stageId;

    //@ApiProperty(name = "stageName",desc="名称")
    private String stageName;

    //@ApiProperty(name = "createTime",desc="创建时间")
    private String createTime;

    //@ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    //@ApiProperty(name="stageSort",desc="阶段顺序")
    private int stageSort;

    //@ApiProperty(name = "parentId",desc="主阶段")
    private String parentId;

    //@ApiProperty(name = "code",desc="是否是源码")
    private boolean code = false;

    //@ApiProperty(name = "taskValues",desc="阶段任务")
    private List<Tasks> taskValues;

    //@ApiProperty(name = "stageList",desc="阶段")
    private List<Stage> stageList;

    //@ApiProperty(name = "taskType",desc="任务类型")
    private String taskType;

    //@ApiProperty(name = "taskId",desc="任务id")
    private String taskId;

    //@ApiProperty(name = "values",desc="更新内容")
    private Object values;

    //@ApiProperty(name = "taskSort",desc="任务顺序")
    private int taskSort;

    //@ApiProperty(name = "parallelName",desc="并行阶段名称")
    private String parallelName;

    // 执行实例id
    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getStageId() {
        return stageId;
    }

    public Stage setStageId(String stageId) {
        this.stageId = stageId;
        return this;
    }

    public String getStageName() {
        return stageName;
    }

    public Stage setStageName(String stageName) {
        this.stageName = stageName;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Stage setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public Stage setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public int getStageSort() {
        return stageSort;
    }

    public Stage setStageSort(int stageSort) {
        this.stageSort = stageSort;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public Stage setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public boolean isCode() {
        return code;
    }

    public Stage setCode(boolean code) {
        this.code = code;
        return this;
    }

    public List<Tasks> getTaskValues() {
        return taskValues;
    }

    public Stage setTaskValues(List<Tasks> taskValues) {
        this.taskValues = taskValues;
        return this;
    }

    public List<Stage> getStageList() {
        return stageList;
    }

    public Stage setStageList(List<Stage> stageList) {
        this.stageList = stageList;
        return this;
    }

    public String getTaskType() {
        return taskType;
    }

    public Stage setTaskType(String taskType) {
        this.taskType = taskType;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public Stage setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public Object getValues() {
        return values;
    }

    public Stage setValues(Object values) {
        this.values = values;
        return this;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public Stage setTaskSort(int taskSort) {
        this.taskSort = taskSort;
        return this;
    }

    public String getParallelName() {
        return parallelName;
    }

    public Stage setParallelName(String parallelName) {
        this.parallelName = parallelName;
        return this;
    }
}
