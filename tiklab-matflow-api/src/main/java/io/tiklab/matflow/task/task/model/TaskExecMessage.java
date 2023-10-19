package io.tiklab.matflow.task.task.model;

import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.task.task.model.Tasks;


/**
 * 执行发送消息通知需要的信息
 */
public class TaskExecMessage {

    //@ApiProperty(name = "pipeline",desc = "流水线信息")
    private Pipeline pipeline;

    //@ApiProperty(name = "tasks",desc = "任务信息")
    private Tasks tasks;

    //@ApiProperty(name = "taskName",desc = "需要执行任务的名称")
    private String taskName;

    //@ApiProperty(name = "taskId",desc = "需要执行任务的Id")
    private String taskId;

    //@ApiProperty(name = "execState",desc = "执行任务的状态")
    private boolean execState;

    //@ApiProperty(name = "execPipeline",desc = "是任务还是流水线")
    private boolean execPipeline;


    public TaskExecMessage() {
    }

    public TaskExecMessage(Pipeline pipeline, boolean execState) {
        this.pipeline = pipeline;
        this.execState = execState;
    }

    public TaskExecMessage(Pipeline pipeline, String taskName, String taskId, boolean execState) {
        this.pipeline = pipeline;
        this.taskName = taskName;
        this.taskId = taskId;
        this.execState = execState;
    }

    public TaskExecMessage(Pipeline pipeline, Tasks tasks, String taskName, boolean execState, boolean execPipeline) {
        this.pipeline = pipeline;
        this.tasks = tasks;
        this.taskName = taskName;
        this.execState = execState;
        this.execPipeline = execPipeline;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isExecState() {
        return execState;
    }

    public void setExecState(boolean execState) {
        this.execState = execState;
    }

    public boolean isExecPipeline() {
        return execPipeline;
    }

    public void setExecPipeline(boolean execPipeline) {
        this.execPipeline = execPipeline;
    }
}
