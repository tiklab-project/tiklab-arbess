package net.tiklab.matflow.support.trigger.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.support.trigger.model.TriggerTime;

import java.util.List;

@JoinProvider(model = TriggerTime.class)
public interface TriggerTimeService {

    /**
     * 创建
     * @param triggerTime time信息
     * @return timeId
     */
    String createTime(TriggerTime triggerTime) ;


    /**
     * 创建所有关联时间信息
     * @param triggerTime 信息
     */
    String createTimeConfig(TriggerTime triggerTime, String pipelineId);


    /**
     * 根据配置id查询消息类型
     * @param configId 配置id
     * @return 消息
     */
    TriggerTime findTimeConfig(String configId);


    /**
     * 根据配置查询所有任务
     * @param configId 配置id
     * @return 任务集合
     */
    List<TriggerTime> findAllTimeConfig(String configId);


    /**
     * 根据类型查询定时任务
     * @param configId 配置id
     * @param cron 表达式
     * @return 配置
     */
    TriggerTime fondCronConfig(String configId, String cron);


    /**
     * 根据配置获取所有时间
     * @param configId 配置id
     * @return 时间集合
     */
    List<Integer> findAllDataConfig(String configId);

    /**
     * 删除当前配置下的的所有任务
     * @param configId 配置id
     */
    void deleteAllTime(String configId,String pipelineId);


    /**
     * 周期任务更新执行时间
     * @param timeId 任务id
     */
    void deleteCronTime(String pipelineId,String timeId);

    /**
     * 删除
     * @param timeId timeId
     */
    void deleteTime(String timeId) ;


    /**
     * 更新信息
     * @param triggerTime 信息
     */
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

    @FindList
    List<TriggerTime> findAllTimeList(List<String> idList);
    
    
}
