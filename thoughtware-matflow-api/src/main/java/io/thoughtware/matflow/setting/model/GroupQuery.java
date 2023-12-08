package io.thoughtware.matflow.setting.model;

import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.join.annotation.Join;

import java.util.List;


public class GroupQuery {

    // 环境名称
    private String groupName;

    // 用户ID
    private String userId;

    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


    public String getGroupName() {
        return groupName;
    }

    public GroupQuery setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public GroupQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public GroupQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public GroupQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }
}
