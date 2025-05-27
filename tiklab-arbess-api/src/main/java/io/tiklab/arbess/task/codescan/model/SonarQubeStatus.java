package io.tiklab.arbess.task.codescan.model;

public class SonarQubeStatus {

    // 说明通过了质量阈（可为 OK / ERROR / WARN）
    private String status;

    // 是否忽略了质量阈
    private Boolean ignoredConditions;

    public SonarQubeStatus() {
    }

    public SonarQubeStatus(String status, Boolean ignoredConditions) {
        this.status = status;
        this.ignoredConditions = ignoredConditions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIgnoredConditions() {
        return ignoredConditions;
    }

    public void setIgnoredConditions(Boolean ignoredConditions) {
        this.ignoredConditions = ignoredConditions;
    }
}
