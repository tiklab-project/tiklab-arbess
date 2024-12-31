package io.tiklab.arbess.support.agent.model;

import io.tiklab.core.BaseModel;
import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

/**
 * 代理查询模型
 */
public class AgentQuery extends BaseModel {

    /**
     * 代理名称
     */
    private String name;

    /**
     * 代理IP
     */
    private String ip;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 代理地址
     */
    private String address;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 分页参数
     */
    private Page pageParam= new Page();

    /**
     * 排序参数
     */
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
