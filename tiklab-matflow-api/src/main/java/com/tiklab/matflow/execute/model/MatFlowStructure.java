package com.tiklab.matflow.execute.model;


import com.tiklab.beans.annotation.Mapper;
import com.tiklab.join.annotation.Join;
import com.tiklab.postlink.annotation.ApiModel;
import com.tiklab.postlink.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "MatFlowStructureEntity")
public class MatFlowStructure {

    //id
    @ApiProperty(name = "structureId",desc="id")
    private String structureId;

    //构建类型
    @ApiProperty(name = "type",desc="构建类型")
    private int type;

    //构建文件地址
    @ApiProperty(name="structureAddress",desc="构建文件地址")
    private String structureAddress;

    //构建命令
    @ApiProperty(name="structureOrder",desc="构建命令")
    private String structureOrder;

    //顺序
    @ApiProperty(name = "sort",desc="顺序")
    private int sort;

    //别名
    @ApiProperty(name = "structureAlias",desc="别名")
    private String structureAlias;

    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStructureAddress() {
        return structureAddress;
    }

    public void setStructureAddress(String structureAddress) {
        this.structureAddress = structureAddress;
    }

    public String getStructureOrder() {
        return structureOrder;
    }

    public void setStructureOrder(String structureOrder) {
        this.structureOrder = structureOrder;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStructureAlias() {
        return structureAlias;
    }

    public void setStructureAlias(String structureAlias) {
        this.structureAlias = structureAlias;
    }
}
