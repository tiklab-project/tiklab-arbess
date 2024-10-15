package io.tiklab.arbess.setting.model;


import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class AuthHostK8sQuery {

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

    public AuthHostK8sQuery setName(String name) {
        this.name = name;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public AuthHostK8sQuery setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public AuthHostK8sQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getType() {
        return type;
    }

    public AuthHostK8sQuery setType(String type) {
        this.type = type;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public AuthHostK8sQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public AuthHostK8sQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }
}
