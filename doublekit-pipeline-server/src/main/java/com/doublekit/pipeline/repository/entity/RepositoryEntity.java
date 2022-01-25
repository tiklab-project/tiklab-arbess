package com.doublekit.pipeline.repository.entity;


import com.doublekit.dal.jpa.mapper.annotation.Column;
import com.doublekit.dal.jpa.mapper.annotation.GeneratorValue;
import com.doublekit.dal.jpa.mapper.annotation.Id;
import com.doublekit.dal.jpa.mapper.annotation.Table;import com.doublekit.dal.jpa.mapper.annotation.Entity;

import java.io.Serializable;

@Entity @Table(name="wiki_repository")
public class RepositoryEntity implements Serializable {

    @Id
    @GeneratorValue
    @Column(name = "id",length = 32)
    private String id;

    //名称
    @Column(name = "name",length = 64,notNull = true)
    private String name;

    //知识库类别
    @Column(name = "type_id",length = 32,notNull = true)
    private String typeId;

    //管理员
    @Column(name = "master",length = 32,notNull = true)
    private String master;

    //权限范围 0 全部可见 1成员可见
    @Column(name = "limits",length = 32,notNull = true)
    private String limits;

    //描述
    @Column(name = "description",length = 64)
    private String desc;

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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits;
    }
}
