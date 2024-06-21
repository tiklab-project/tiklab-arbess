package io.thoughtware.matflow.home.model;

import java.util.List;
import java.util.Map;

public class MessageDetail {

    private String pipelineId;

    private Map<String, Object> map;

    private List<String> receiver;

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<String> receiver) {
        this.receiver = receiver;
    }
}
