package com.tiklab.matflow.setting.path.model;



import com.tiklab.beans.annotation.Mapper;
import com.tiklab.join.annotation.Join;
import com.tiklab.postin.annotation.ApiModel;
import com.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "MatFlowPathEntity")
public class MatFlowPath {

    @ApiProperty(name = "pathId",desc = "id")
    private String pathId;

    @ApiProperty(name = "pathType",desc = "类型")
    private int pathType;

    @ApiProperty(name = "pathName",desc = "名称")
    private String pathName;

    @ApiProperty(name = "pathAddress",desc = "地址")
    private String pathAddress;

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public int getPathType() {
        return pathType;
    }

    public void setPathType(int pathType) {
        this.pathType = pathType;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathAddress() {
        return pathAddress;
    }

    public void setPathAddress(String pathAddress) {
        this.pathAddress = pathAddress;
    }
}
