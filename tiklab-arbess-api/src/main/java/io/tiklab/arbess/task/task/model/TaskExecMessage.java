package io.tiklab.arbess.task.task.model;

import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.support.message.model.TaskMessage;
import io.tiklab.user.user.model.User;

import java.util.ArrayList;
import java.util.List;


/**
 * 执行发送消息通知需要的信息
 */
public class TaskExecMessage {

    //@ApiProperty(name = "pipeline",desc = "流水线信息")
    private Pipeline pipeline;

    //@ApiProperty(name = "execState",desc = "执行任务的状态")
    private boolean execState;

    //@ApiProperty(name = "execPipeline",desc = "是任务还是流水线")
    private boolean execPipeline;

    // 任务名称
    private String taskName;

    private String pipelineId;

    private String instanceId;

    private User execUser;

    public User getExecUser() {
        return execUser;
    }

    public void setExecUser(User execUser) {
        this.execUser = execUser;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public TaskExecMessage setInstanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    // 消息配置
    private List<TaskMessage> messageList = new ArrayList<>();

    public List<TaskMessage> getMessageList() {
        return messageList;
    }

    public TaskExecMessage setMessageList(List<TaskMessage> messageList) {
        this.messageList = messageList;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public TaskExecMessage() {
    }

    public TaskExecMessage(Pipeline pipeline, boolean execState) {
        this.pipeline = pipeline;
        this.execState = execState;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
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
