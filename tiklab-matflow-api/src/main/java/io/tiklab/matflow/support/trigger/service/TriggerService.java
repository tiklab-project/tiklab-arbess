package io.tiklab.matflow.support.trigger.service;

import io.tiklab.matflow.support.trigger.model.Trigger;

import java.util.List;
/**
 * 流水线触发器服务接口
 */
public interface TriggerService {


    /**
     * 创建配置及任务
     * @param config 配置
     * @return 配置id
     */
     String createConfig(Trigger config);


    /**
     * 查询所有配置
     * @param pipelineId 流水线id
     * @return 配置列表
     */
    List<Object> findAllConfig(String pipelineId);

    /**
     * 删除流水线所有定时任务
     * @param pipelineId 流水线id
     */
    void deleteAllConfig(String pipelineId);


    /**
     * 删除单个定时任务
     * @param pipelineId 流水线id
     * @param cron 表达式
     */
    void deleteCronConfig(String pipelineId,String cron);

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
    void updateConfig(Trigger config);


    /**
     * 根据流水线id查询触发器配置
     * @param pipelineId 流水线id
     * @return 配置
     */
     List<Trigger> findAllTriggerConfig(String pipelineId) ;


    //更新
     void updateTriggerConfig(Trigger trigger) ;

    //查询单个
    Trigger findOneTriggerConfig(String triggerConfigId) ;

    //删除
    void deleteTriggerConfig(String triggerConfigId) ;
    //查询所有
    List<Trigger> findAllTriggerConfig() ;

    List<Trigger> findAllTriggerConfigList(List<String> idList) ;

}
