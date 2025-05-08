package io.tiklab.arbess.support.approve.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.List;

@Join
@Mapper
public class ApproveQuery {

    // 流水线ID列表
    private List<String> pipelineIdList;

    // 流水线ID
    private String pipelineId;

    // 状态
    private String status;

    // 用户ID
    private String userId;

    // 分页参数
    private Page pageParam= new Page();

    // 排序参数
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


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

    public List<String> getPipelineIdList() {
        return pipelineIdList;
    }

    public void setPipelineIdList(List<String> pipelineIdList) {
        this.pipelineIdList = pipelineIdList;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}














