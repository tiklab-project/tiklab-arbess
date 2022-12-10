package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.PipelinePost;

public interface PipelinePostTaskServer {


    /**
     * 配置信息
     * @param config 配置
     */
     void updateConfig(PipelinePost config);

    /**
     * 查询配置信息
     * @param config 配置
     * @return 配置
     */
    Object findOneConfig(PipelinePost config);


    /**
     * 删除配置
     * @param config 配置
     */
    void deleteConfig(PipelinePost config);


}