package com.tiklab.matflow.setting.envConfig.service;

import com.tiklab.matflow.setting.envConfig.model.MatFlowEnvConfig;

import java.util.List;

public interface MatFlowEnvConfigService {


    String createMatFlowEnvConfig(MatFlowEnvConfig matFlowEnvConfig);

    //删除
    void deleteMatFlowEnvConfig(String matFlowConfigId);

    //更新
    void updateMatFlowEnvConfig(MatFlowEnvConfig matFlowEnvConfig);

    //查询
    MatFlowEnvConfig findOneMatFlowEnvConfig(String matFlowConfigId);

    //查询所有
    List<MatFlowEnvConfig> findAllMatFlowEnvConfig();

    /**
     * 获取配置
     * @param type 类型
     * @return 配置信息
     */
    MatFlowEnvConfig findOneMatFlowEnvConfig(Integer type);

    List<MatFlowEnvConfig> selectMatFlowEnvConfigList(List<String> idList);

}
