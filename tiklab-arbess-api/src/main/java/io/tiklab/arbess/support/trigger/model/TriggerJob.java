package io.tiklab.arbess.support.trigger.model;

import io.tiklab.core.BaseModel;

public class TriggerJob extends BaseModel {


    private String group;

    private String pipelineId;

    private Class jobClass;

    private String cron;

    private String triggerId;

    private String timeId;

    public String getGroup() {
        return group;
    }

    public TriggerJob setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public TriggerJob setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public Class getJobClass() {
        return jobClass;
    }

    public TriggerJob setJobClass(Class jobClass) {
        this.jobClass = jobClass;
        return this;
    }

    public String getCron() {
        return cron;
    }

    public TriggerJob setCron(String cron) {
        this.cron = cron;
        return this;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public TriggerJob setTriggerId(String triggerId) {
        this.triggerId = triggerId;
        return this;
    }

    public String getTimeId() {
        return timeId;
    }

    public TriggerJob setTimeId(String timeId) {
        this.timeId = timeId;
        return this;
    }
}
