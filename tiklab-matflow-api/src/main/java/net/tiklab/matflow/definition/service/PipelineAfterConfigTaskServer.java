package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.PipelineAfterConfig;

public interface PipelineAfterConfigTaskServer {


    /**
     * 配置信息
     * @param config 配置
     */
     void updateConfig(PipelineAfterConfig config);

    /**
     * 查询配置信息
     * @param config 配置
     * @return 配置
     */
    Object findOneConfig(PipelineAfterConfig config);


    /**
     * 删除配置
     * @param config 配置
     */
    void deleteConfig(PipelineAfterConfig config);


}
