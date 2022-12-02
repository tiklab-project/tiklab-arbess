package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.PipelineBeforeConfig;

import java.util.List;

public interface PipelineBeforeConfigServer {


    /**
     * 创建配置及任务
     * @param config 配置
     * @return 配置id
     */
     String createConfig(PipelineBeforeConfig config);


    /**
     * 查询所有配置
     * @param pipelineId 流水线id
     * @return 配置列表
     */
    List<Object> findAllConfig(String pipelineId);

    /**
     * 查询单个任务
     * @param configId 配置id
     * @return 任务
     */
    Object findOneConfig(String configId);


    /**
     * 更新配置信息
     * @param config 配置
     */
    void updateConfig(PipelineBeforeConfig config);


    /**
     * 根据流水线id查询触发器配置
     * @param pipelineId 流水线id
     * @return 配置
     */
     List<PipelineBeforeConfig> findAllBeforeConfig(String pipelineId) ;


    //更新
     void updateBeforeConfig(PipelineBeforeConfig pipelineBeforeConfig) ;

    //查询单个
    PipelineBeforeConfig findOneBeforeConfig(String beforeConfigId) ;

    //删除
    void deleteBeforeConfig(String beforeConfigId) ;
    //查询所有
    List<PipelineBeforeConfig> findAllBeforeConfig() ;

    List<PipelineBeforeConfig> findAllBeforeConfigList(List<String> idList) ;

}
