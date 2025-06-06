package io.tiklab.arbess.support.message.model;

import io.tiklab.core.BaseModel;
import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class TaskMessageQuery extends BaseModel {

    // all 全局 task 任务
    private Integer type;

    private String pipelineId;

    private String taskId;

    private Page pageParam= new Page();

    private List<Order> orderParams = OrderBuilders.instance().desc("id").get();

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public TaskMessageQuery setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskMessageQuery setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public TaskMessageQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public TaskMessageQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }
}
