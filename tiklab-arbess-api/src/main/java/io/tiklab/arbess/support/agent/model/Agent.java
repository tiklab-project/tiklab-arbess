package io.tiklab.arbess.support.agent.model;

import io.tiklab.core.BaseModel;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 代理模型
 */
@Join
@Mapper
public class Agent extends BaseModel {

    /**
     * 代理ID
     */
    private String id;

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
     * 创建时间
     */
    private String createTime;

    /**
     * 是否连接
     */
    private Boolean isConnect;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Boolean getConnect() {
        return isConnect;
    }

    public void setConnect(Boolean connect) {
        isConnect = connect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
