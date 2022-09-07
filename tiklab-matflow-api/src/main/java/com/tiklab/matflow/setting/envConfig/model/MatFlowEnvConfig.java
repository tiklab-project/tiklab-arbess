package com.tiklab.matflow.setting.envConfig.model;



import com.tiklab.postin.annotation.ApiModel;
import com.tiklab.postin.annotation.ApiProperty;

@ApiModel
public class MatFlowEnvConfig {

    @ApiProperty(name = "configId",desc = "id")
    private String configId;

    //git
    @ApiProperty(name = "configType",desc = "类型")
    private int configType;

    //svn
    @ApiProperty(name = "configAddress",desc = "地址")
    private String configAddress;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public int getConfigType() {
        return configType;
    }

    public void setConfigType(int configType) {
        this.configType = configType;
    }

    public String getConfigAddress() {
        return configAddress;
    }

    public void setConfigAddress(String configAddress) {
        this.configAddress = configAddress;
    }
}
