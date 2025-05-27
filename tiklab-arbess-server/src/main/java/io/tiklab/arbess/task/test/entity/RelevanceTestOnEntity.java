package io.tiklab.arbess.task.test.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_relevance_teston")
public class RelevanceTestOnEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "relevance_id" ,notNull = true)
    private String relevanceId;

    @Column(name = "teston_id" ,notNull = true)
    private String testonId;

    @Column(name = "test_plan_id" ,notNull = true)
    private String testPlanId;

    @Column(name = "pipeline_id" ,notNull = true)
    private String pipelineId;

    @Column(name = "auth_id" ,notNull = true)
    private String authId;

    @Column(name = "create_time" ,notNull = true)
    private String createTime;

    public String getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(String testPlanId) {
        this.testPlanId = testPlanId;
    }

    public String getAuthId() {
        return authId;
    }

    public RelevanceTestOnEntity setAuthId(String authId) {
        this.authId = authId;
        return this;
    }

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTestonId() {
        return testonId;
    }

    public void setTestonId(String testonId) {
        this.testonId = testonId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
