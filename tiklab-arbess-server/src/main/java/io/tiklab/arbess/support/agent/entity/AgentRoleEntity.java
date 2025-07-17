package io.tiklab.arbess.support.agent.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_agent_role")
public class AgentRoleEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    // 类型  1.使用默认，2.优先默认，3.优先空闲
    @Column(name = "type")
    private Integer type;

    // 状态  1.启用，2.禁用
    @Column(name = "status")
    private Integer status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
