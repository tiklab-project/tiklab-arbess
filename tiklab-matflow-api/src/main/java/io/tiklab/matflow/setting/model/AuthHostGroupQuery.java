package io.tiklab.matflow.setting.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class AuthHostGroupQuery {

    private String groupName;


    private String userId;


    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


    public String getGroupName() {
        return groupName;
    }

    public AuthHostGroupQuery setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public AuthHostGroupQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public AuthHostGroupQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public AuthHostGroupQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }
}
