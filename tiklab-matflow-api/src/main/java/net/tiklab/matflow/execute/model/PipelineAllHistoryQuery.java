package net.tiklab.matflow.execute.model;

import net.tiklab.core.order.Order;
import net.tiklab.core.order.OrderBuilders;
import net.tiklab.core.page.Page;
import net.tiklab.matflow.definition.model.Pipeline;

import java.util.List;

/**
 * 首页历史分页
 */

public class PipelineAllHistoryQuery {

    /**
     * 流水线id
     */
    private String pipelineId;

    /**
     * 流水线执行状态
     */
    private int state;

    /**
     * 流水线类型
     */
    private int type;

    /**
     * 用户流水线
     */
    private List<Pipeline> pipelineList;

    /**
     * 分页参数
     */
    private Page pageParam= new Page();

    /**
     * 排序参数
     */
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Pipeline> getPipelineList() {
        return pipelineList;
    }

    public void setPipelineList(List<Pipeline> pipelineList) {
        this.pipelineList = pipelineList;
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





































