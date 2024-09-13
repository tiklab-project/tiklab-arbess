package io.thoughtware.arbess.task.codescan.model;

import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;

import java.util.List;

/**
 * 代码扫描查询
 * @author zcamy
 */
public class SpotbugsBugQuery {

    private String pipelineId;

    private Page pageParam= new Page();

    private List<Order> orderParams = OrderBuilders.instance().desc("scanTime").get();

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public SpotbugsBugQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public SpotbugsBugQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public SpotbugsBugQuery setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }
}
