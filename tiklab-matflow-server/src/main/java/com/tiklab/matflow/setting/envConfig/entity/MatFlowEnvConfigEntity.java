package com.tiklab.matflow.setting.envConfig.entity;


import com.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="matflow_config")
public class MatFlowEnvConfigEntity {

    //凭证id
    @Id
    @GeneratorValue
    @Column(name = "config_id")
    private String configId;

    //1.git 2.svn 11.node 12.maven
    @Column(name = "config_type")
    private int configType;

    //名称
    @Column(name = "config_name")
    private String configName;

    //地址
    @Column(name = "config_address")
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

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }
}
