package io.tiklab.arbess.task.test.model;

public class TestOnRelevance {

    private String pipelineId;

    private String instanceId;

    private String authId;

    private String testPlanId;

    public String getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(String testPlanId) {
        this.testPlanId = testPlanId;
    }

    public TestOnRelevance() {
    }

    public TestOnRelevance(String pipelineId, String instanceId, String authId) {
        this.pipelineId = pipelineId;
        this.instanceId = instanceId;
        this.authId = authId;
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

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }
}
