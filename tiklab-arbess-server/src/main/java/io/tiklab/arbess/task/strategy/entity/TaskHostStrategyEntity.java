package io.tiklab.arbess.task.strategy.entity;

import io.tiklab.dal.jpa.annotation.Column;
import io.tiklab.dal.jpa.annotation.Entity;
import io.tiklab.dal.jpa.annotation.Id;
import io.tiklab.dal.jpa.annotation.Table;

@Entity
@Table(name="pip_task_strategy")
public class TaskHostStrategyEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    @Column(name = "strategy_type" )
    private Integer strategyType;

    // 等待时间
    @Column(name = "wail_time" )
    private Integer wailTime;

    //审批人id
    @Column(name = "inspect_ids")
    private String inspectIds;

    // 运行命令
    @Column(name = "run_order")
    private String order;

    @Column(name = "order_type")
    private Integer orderType;

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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
