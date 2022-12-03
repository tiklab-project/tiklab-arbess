package net.tiklab.matflow.trigger.server;

import net.tiklab.matflow.trigger.model.PipelineTriggerConfig;

import java.util.List;

public interface PipelineTriggerConfigServer {


    /**
     * 创建配置及任务
     * @param config 配置
     * @return 配置id
     */
     String createConfig(PipelineTriggerConfig config);


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
    void updateConfig(PipelineTriggerConfig config);


    /**
     * 根据流水线id查询触发器配置
     * @param pipelineId 流水线id
     * @return 配置
     */
     List<PipelineTriggerConfig> findAllTriggerConfig(String pipelineId) ;


    //更新
     void updateTriggerConfig(PipelineTriggerConfig pipelineTriggerConfig) ;

    //查询单个
    PipelineTriggerConfig findOneTriggerConfig(String triggerConfigId) ;

    //删除
    void deleteTriggerConfig(String triggerConfigId) ;
    //查询所有
    List<PipelineTriggerConfig> findAllTriggerConfig() ;

    List<PipelineTriggerConfig> findAllTriggerConfigList(List<String> idList) ;

}
