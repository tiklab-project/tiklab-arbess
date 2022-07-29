package com.tiklab.matflow.instance.model;



import com.tiklab.core.order.Order;
import com.tiklab.core.order.OrderBuilders;
import com.tiklab.core.page.Page;
import com.tiklab.postlink.annotation.ApiModel;
import com.tiklab.postlink.annotation.ApiProperty;

import java.util.List;

/**
 * 流水线历史筛选条件
 */

@ApiModel
public class MatFlowHistoryQuery {

    @ApiProperty(name ="matFlowId",desc = "流水线id")
    private String matFlowId;

    @ApiProperty(name ="state",desc = "状态")
    private int state;

    @ApiProperty(name ="userId",desc = "用户id")
    private String userId;

    @ApiProperty(name ="type",desc = "类型")
    private int type;

    @ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    @ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


    public String getMatFlowId() {
        return matFlowId;
    }

    public void setMatFlowId(String matFlowId) {
        this.matFlowId = matFlowId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
