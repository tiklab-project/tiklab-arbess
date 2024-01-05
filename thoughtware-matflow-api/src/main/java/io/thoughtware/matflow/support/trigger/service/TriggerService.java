package io.thoughtware.matflow.support.trigger.service;

import io.thoughtware.matflow.support.trigger.model.Trigger;
import io.thoughtware.matflow.support.trigger.model.TriggerQuery;

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
     * 查询所有配置
     * @param triggerQuery 流水线id
     * @return 配置列表
     */
    List<Object> findAllTrigger(TriggerQuery triggerQuery);


    void cloneTrigger(String pipelineId,String clonePipelineId);

    /**
     * 删除流水线所有定时任务
     * @param pipelineId 流水线id
     */
    void deleteAllTrigger(String pipelineId);


    /**
     * 更新单个定时任务
     * @param pipelineId 定时任务id
     */
    void updateTrigger(String triggerId);

    /**
     * 更新配置信息
     * @param trigger 配置
     */
    void updateTrigger(Trigger trigger);

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


    List<Trigger> findAllTrigger();


    Trigger findOneTriggerById(String triggerId);


    List<Trigger> findAllTriggerConfigList(List<String> idList) ;

}































