package io.tiklab.arbess.setting.k8s.model;


import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class KubectlQuery {

    private String userId;

    private String name;

    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();



    public String getName() {
        return name;
    }

    public KubectlQuery setName(String name) {
        this.name = name;
        return this;
    }


    public String getUserId() {
        return userId;
    }

    public KubectlQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public KubectlQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public KubectlQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }
}
