package io.tiklab.arbess.pipeline.execute.model;

import io.tiklab.arbess.setting.tool.model.Scm;
import io.tiklab.arbess.stages.model.Stage;
import io.tiklab.arbess.support.agent.model.Agent;
import io.tiklab.arbess.support.postprocess.model.Postprocess;
import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.task.build.model.TaskBuildProduct;
import io.tiklab.arbess.task.task.model.TaskExecMessage;

import java.util.List;

/**
 * 流水线详情类
 * 包含流水线的详细信息和相关配置
 */
public class PipelineDetails {

    // 应用数据保存地址
    private String dataHome;

    // 应用数据保存地址
    private String sourceDir;

    // 日志保存位置
    private String logDir;

    // 应用地址
    private String applyHome;

    // 流水线ID
    private String pipelineId;

    // 实例ID
    private String instanceId;

    // 用户ID
    private String userId;

    // 运行方式
    private Integer runWay;

    private Agent agent;

    // 阶段任务
    private List<Stage> stageList;

    // 环境
    private List<Scm>  scmList;

    // 变量
    private List<Variable> variableList;

    // 后置处理
    private List<Postprocess> postprocessList;

    private TaskExecMessage taskExecMessage;

    private TaskBuildProduct taskBuildProduct;

    public TaskBuildProduct getTaskBuildProduct() {
        return taskBuildProduct;
    }

    public void setTaskBuildProduct(TaskBuildProduct taskBuildProduct) {
        this.taskBuildProduct = taskBuildProduct;
    }

    public TaskExecMessage getTaskExecMessage() {
        return taskExecMessage;
    }

    public void setTaskExecMessage(TaskExecMessage taskExecMessage) {
        this.taskExecMessage = taskExecMessage;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getDataHome() {
        return dataHome;
    }

    public void setDataHome(String dataHome) {
        this.dataHome = dataHome;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public String getLogDir() {
        return logDir;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    public String getApplyHome() {
        return applyHome;
    }

    public void setApplyHome(String applyHome) {
        this.applyHome = applyHome;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRunWay() {
        return runWay;
    }

    public void setRunWay(Integer runWay) {
        this.runWay = runWay;
    }

    public List<Stage> getStageList() {
        return stageList;
    }

    public void setStageList(List<Stage> stageList) {
        this.stageList = stageList;
    }

    public List<Scm> getScmList() {
        return scmList;
    }

    public void setScmList(List<Scm> scmList) {
        this.scmList = scmList;
    }

    public List<Variable> getVariableList() {
        return variableList;
    }

    public void setVariableList(List<Variable> variableList) {
        this.variableList = variableList;
    }

    public List<Postprocess> getPostprocessList() {
        return postprocessList;
    }

    public void setPostprocessList(List<Postprocess> postprocessList) {
        this.postprocessList = postprocessList;
    }
}
