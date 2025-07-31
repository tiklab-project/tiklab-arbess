package io.tiklab.arbess.task.strategy.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.ArrayList;
import java.util.List;

@Join
@Mapper
public class TaskHostStrategy {

    private String taskId;

    // 策略类型
    private Integer strategyType;

    // 等待时间
    private Integer wailTime;

    //审批人id
    private String inspectIds;

    // 运行命令
    private String order;

    // 命令类型
    private Integer orderType;

    private List<String> inspectIdList = new ArrayList<>();

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public List<String> getInspectIdList() {
        return inspectIdList;
    }

    public void setInspectIdList(List<String> inspectIdList) {
        this.inspectIdList = inspectIdList;
    }

    public Integer getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Integer strategyType) {
        this.strategyType = strategyType;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getWailTime() {
        return wailTime;
    }

    public void setWailTime(Integer wailTime) {
        this.wailTime = wailTime;
    }

    public String getInspectIds() {
        return inspectIds;
    }

    public void setInspectIds(String inspectIds) {
        this.inspectIds = inspectIds;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
