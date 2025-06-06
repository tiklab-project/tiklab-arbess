package io.tiklab.arbess.support.message.model;

import java.util.List;
import java.util.Map;

public class TaskMessageSendDetail {

    /**
     * 流水线id
     */
    private String pipelineId;

    /**
     * 接收人
     */
    private List<String> list;


    /**
     * 消息内容
     */
    private Map<String, Object> map;

    public TaskMessageSendDetail() {
    }

    public TaskMessageSendDetail(String pipelineId, List<String> list, Map<String, Object> map) {
        this.pipelineId = pipelineId;
        this.list = list;
        this.map = map;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}



