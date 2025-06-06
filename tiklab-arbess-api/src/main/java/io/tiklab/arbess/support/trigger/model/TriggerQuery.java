package io.tiklab.arbess.support.trigger.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.List;


/**
 * 流水线触发器模型
 */
//@ApiModel
@Join
@Mapper
public class TriggerQuery {


    //@ApiProperty(name = "name",desc="名称")
    private String name;

    /**
     * 流水线ID
     */
    private String pipelineId;

    /**
     * 状态 1--未执行 2--已执行
     */
    private Integer state;

    private Page pageParam= new Page();

    private List<Order> orderParams = OrderBuilders.instance().desc("id").get();


    public String getName() {
        return name;
    }

    public TriggerQuery setName(String name) {
        this.name = name;
        return this;
    }


    public Integer getState() {
        return state;
    }

    public TriggerQuery setState(Integer state) {
        this.state = state;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public TriggerQuery setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public void setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
    }
}

