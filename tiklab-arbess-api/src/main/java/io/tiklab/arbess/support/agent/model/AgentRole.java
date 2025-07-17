package io.tiklab.arbess.support.agent.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

@Join
@Mapper
public class AgentRole {

    private String id;

    /**
     * 类型 1.使用默认，2.优先默认，3.优先空闲
     */
    private Integer type;

    // 状态  1.使用，2.禁用
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
