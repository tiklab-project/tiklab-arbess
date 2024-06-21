package io.thoughtware.matflow.pipeline.execute.model;

import io.thoughtware.matflow.setting.model.Scm;
import io.thoughtware.matflow.stages.model.Stage;
import io.thoughtware.matflow.support.agent.model.Agent;
import io.thoughtware.matflow.support.postprocess.model.Postprocess;
import io.thoughtware.matflow.support.variable.model.Variable;

import java.util.List;

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
