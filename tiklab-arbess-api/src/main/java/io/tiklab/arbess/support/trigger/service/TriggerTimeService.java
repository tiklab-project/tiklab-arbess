package io.tiklab.arbess.support.trigger.service;

import io.tiklab.arbess.support.trigger.model.TriggerTime;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;
/**
 * 流水线触发器时间服务接口
 */
@JoinProvider(model = TriggerTime.class)
public interface TriggerTimeService {

    /**
     * 创建所有关联时间信息
     * @param triggerTime 信息
     */
    String createTriggerTime(TriggerTime triggerTime, String pipelineId);


    /**
     * 根据配置id查询消息类型
     * @param triggerId 配置id
     * @return 消息
     */
    TriggerTime findTriggerTime(String triggerId);


    /**
     * 根据配置查询所有任务
     * @param triggerId 配置id
     * @return 任务集合
     */
    List<TriggerTime> findAllTriggerTime(String triggerId);


    /**
     * 查询cron时间列表
     * @param cron cron表达式
     * @return 时间列表
     */
    List<TriggerTime> fondCronTimeList(String cron);


    /**
     * 根据配置获取所有时间
     * @param configId 配置id
     * @return 时间集合
     */
    List<Integer> findAllDataConfig(String configId);

    /**
     * 删除当前配置下的的所有任务
     * @param triggerId 配置id
     */
    void deleteAllTime(String triggerId,String pipelineId);


    /**
     * 周期任务更新执行时间
     * @param timeId 任务id
     */
    Boolean deleteCronTime(String pipelineId,String timeId);

    /**
     * 删除
     * @param timeId timeId
     */
    void deleteTime(String timeId) ;


    void updateTime(TriggerTime triggerTime);

    /**
     * 查询单个信息
     * @param timeId pipelineTimeId
     * @return time信息
     */
    @FindOne
    TriggerTime findOneTime(String timeId) ;

    /**
     * 查询所有信息
     * @return time信息集合
     */
    @FindAll
    List<TriggerTime> findAllTime() ;

    /**
     * 根据ID列表查询时间
     * @param idList 时间ID列表
     * @return 时间列表
     */
    @FindList
    List<TriggerTime> findAllTimeList(List<String> idList);
    
    
}
