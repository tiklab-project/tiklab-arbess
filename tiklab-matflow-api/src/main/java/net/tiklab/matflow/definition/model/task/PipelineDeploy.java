package net.tiklab.matflow.definition.model.task;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineDeployEntity")
public class PipelineDeploy {

    @ApiProperty(name = "deployId" , desc = "id")
    private String deployId;

    @ApiProperty(name = "authType",desc = "认证类型" )
    private int authType;

    @ApiProperty(name = "localAddress" , desc = "文件地址")
    private String localAddress;

    @ApiProperty(name="deployAddress",desc="部署地址")
    private String deployAddress;

    @ApiProperty(name = "authId" , desc = "认证id")
    private String authId;

    @ApiProperty(name = "deployOrder",desc = "部署命令" )
    private String deployOrder;

    @ApiProperty(name = "startAddress",desc = "启动文件地址" )
    private String startAddress;

    @ApiProperty(name="startOrder",desc="启动命令")
    private String startOrder;

    //授权信息
    private Object auth;

    private int sort;

    private int type;


    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getDeployAddress() {
        return deployAddress;
    }

    public void setDeployAddress(String deployAddress) {
        this.deployAddress = deployAddress;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getDeployOrder() {
        return deployOrder;
    }

    public void setDeployOrder(String deployOrder) {
        this.deployOrder = deployOrder;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getStartOrder() {
        return startOrder;
    }

    public void setStartOrder(String startOrder) {
        this.startOrder = startOrder;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
