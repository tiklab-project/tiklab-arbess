package io.thoughtware.arbess.task.deploy.model;

import io.thoughtware.arbess.setting.model.AuthHost;

import java.util.List;

public class TaskDeployStrategy {

    private String taskInstanceId;

    private String strategyInstanceId;

    private List<AuthHost> authHostList;


    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public String getStrategyInstanceId() {
        return strategyInstanceId;
    }

    public void setStrategyInstanceId(String strategyInstanceId) {
        this.strategyInstanceId = strategyInstanceId;
    }

    public List<AuthHost> getAuthHostList() {
        return authHostList;
    }

    public void setAuthHostList(List<AuthHost> authHostList) {
        this.authHostList = authHostList;
    }
}
