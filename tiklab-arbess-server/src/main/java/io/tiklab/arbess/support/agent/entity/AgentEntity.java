package io.tiklab.arbess.support.agent.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_agent")
public class AgentEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "ip")
    private String ip;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "address")
    private String address;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "display_type")
    private String displayType;

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
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
