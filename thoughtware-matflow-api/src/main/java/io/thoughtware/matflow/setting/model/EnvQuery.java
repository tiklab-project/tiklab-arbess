package io.thoughtware.matflow.setting.model;

import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.join.annotation.Join;

import java.util.List;


public class EnvQuery {

    // 环境名称
    private String envName;


    // 用户ID
    private String userId;


    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


    public String getEnvName() {
        return envName;
    }

    public EnvQuery setEnvName(String envName) {
        this.envName = envName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public EnvQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public EnvQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public EnvQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }
}
