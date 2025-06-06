package io.tiklab.arbess.support.trigger.service;

import io.tiklab.arbess.support.trigger.model.Trigger;
import io.tiklab.arbess.support.trigger.model.TriggerQuery;

import java.util.List;
/**
 * 流水线触发器服务接口
 */
public interface TriggerService {


    /**
     * 创建配置及任务
     * @param trigger 配置
     * @return 配置id
     */
     String createTrigger(Trigger trigger);

    /**
     * 更新定时任务
     * @param triggerId 定时id
     * @return 配置列表
     */
    void updateTrigger(String triggerId);


    /**
     * 克隆定时任务
     * @param pipelineId 流水线id
     * @param clonePipelineId 克隆流水线id
     */
    void cloneTrigger(String pipelineId,String clonePipelineId);

    /**
     * 删除流水线定时任务
     * @param pipelineId 流水线id
     */
    void deletePipelineTrigger(String pipelineId);

    /**
     * 更新配置信息
     * @param trigger 配置
     */
    void updateTrigger(Trigger trigger);

    /**
     * 根据流水线id查询触发器配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    Trigger findPipelineTrigger(String pipelineId);

    /**
     * 根据流水线id查询触发器配置
     * @param triggerQuery 流水线id
     * @return 配置
     */
     List<Trigger> findTriggerList(TriggerQuery triggerQuery) ;

    /**
     * 删除单个定时任务
     * @param triggerId 定时任务id
     */
    void deleteTrigger(String triggerId) ;


    /**
     * 查询所有配置
     * @return 配置列表
     */
    List<Trigger> findAllTrigger();

    /**
     * 根据ID查询配置
     * @param triggerId 配置ID
     * @return 配置
     */
    Trigger findOneTriggerById(String triggerId);

    /**
     * 根据ID列表查询配置
     * @param idList 配置ID列表
     * @return 配置列表
     */
    List<Trigger> findTriggerList(List<String> idList) ;

}































