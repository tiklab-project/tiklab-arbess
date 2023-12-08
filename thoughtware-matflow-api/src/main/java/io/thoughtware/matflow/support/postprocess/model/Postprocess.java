package io.thoughtware.matflow.support.postprocess.model;

import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.join.annotation.Join;
import io.thoughtware.matflow.task.task.model.Tasks;


/**
 * 流水线后置处理模型
 */
//@ApiModel
@Join
@Mapper
public class Postprocess {

    //@ApiProperty(name = "postId",desc="id")
    private String postId;

    //@ApiProperty(name = "postName",desc="名称")
    private String postName;

    //@ApiProperty(name = "taskType",desc="类型 61:消息通知 71:bat脚本 72:sh脚本")
    private String taskType;

    //@ApiProperty(name = "createTime",desc="创建时间")
    private String createTime;

    //@ApiProperty(name="taskId",desc="任务id")
    private String taskId;

    //@ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    //@ApiProperty(name="values",desc="任务详情")
    private Object values;

    //@ApiProperty(name="task",desc="任务")
    private Tasks task;


    //@ApiProperty(name="taskSort",desc="任务")
    private int taskSort;

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getPostId() {
        return postId;
    }

    public Postprocess setPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public Tasks getTask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
    }

    public String getPostName() {
        return postName;
    }

    public Postprocess setPostName(String postName) {
        this.postName = postName;
        return this;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

}
