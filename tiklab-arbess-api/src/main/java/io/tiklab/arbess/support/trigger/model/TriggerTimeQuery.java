package io.tiklab.arbess.support.trigger.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.List;

public class TriggerTimeQuery {

    private String triggerId;

    private String corn;

    private String taskType;

    private String execStatus;

    private Page pageParam= new Page();

    private List<Order> orderParams = OrderBuilders.instance().desc("triggerId").get();

    public String getTriggerId() {
        return triggerId;
    }

    public TriggerTimeQuery setTriggerId(String triggerId) {
        this.triggerId = triggerId;
        return this;
    }

    public String getCorn() {
        return corn;
    }

    public TriggerTimeQuery setCorn(String corn) {
        this.corn = corn;
        return this;
    }

    public String getTaskType() {
        return taskType;
    }

    public TriggerTimeQuery setTaskType(String taskType) {
        this.taskType = taskType;
        return this;
    }

    public String getExecStatus() {
        return execStatus;
    }

    public TriggerTimeQuery setExecStatus(String execStatus) {
        this.execStatus = execStatus;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public TriggerTimeQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public TriggerTimeQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }
}
