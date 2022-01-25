package com.doublekit.pipeline.repository.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.common.BaseModel;
import com.doublekit.dss.annotation.Index;
import com.doublekit.dss.annotation.IndexField;
import com.doublekit.dss.annotation.IndexId;
import com.doublekit.dss.annotation.IndexQueryField;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.doublekit.user.user.model.User;

import javax.validation.constraints.NotNull;

@ApiModel
@Join
@Index
public class Repository extends BaseModel{

    @ApiProperty(name="id",desc="id")
    @IndexField
    @IndexId
    private java.lang.String id;

    @NotNull
    @ApiProperty(name="name",desc="name",required = true)
    @IndexField
    @IndexQueryField
    private java.lang.String name;

    //@NotNull
    @ApiProperty(name="typeId",desc="typeId",required = true)
    private java.lang.String typeId="1";

    @NotNull
    @ApiProperty(name="master",desc="负责人",eg="@selectOne")
    @Mappings({
            @Mapping(source = "master.id",target = "master")
    })
    @JoinQuery(key = "id")
    private User master;

    //权限范围 0 全部可见 1成员可见
    @ApiProperty(name="limits",desc="limits",required = true)
    private java.lang.String limits;

    @ApiProperty(name="desc",desc="desc")
    private java.lang.String desc;

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }
    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
    public java.lang.String getTypeId() {
        return typeId;
    }

    public void setTypeId(java.lang.String typeId) {
        this.typeId = typeId;
    }

    public java.lang.String getDesc() {
        return desc;
    }

    public void setDesc(java.lang.String desc) {
        this.desc = desc;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits;
    }
}
