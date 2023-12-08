package io.thoughtware.matflow.setting.model;


import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;

import java.util.List;

public class AuthHostQuery {

    private String userId;


    private String type;

    private String name;

    private String ip;

    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();



    public String getName() {
        return name;
    }

    public AuthHostQuery setName(String name) {
        this.name = name;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public AuthHostQuery setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public AuthHostQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getType() {
        return type;
    }

    public AuthHostQuery setType(String type) {
        this.type = type;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public AuthHostQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public AuthHostQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }
}
