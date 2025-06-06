package io.tiklab.arbess.support.trigger.service;

import io.tiklab.arbess.support.trigger.model.TriggerTime;
import io.tiklab.arbess.support.trigger.model.TriggerTimeQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
     * 定时任务执行后更新时间
     * @param timeId timeId
     */
    void updateExecTriggerTime(String timeId,String pipelineId);

    /**
     * 一次流水线定时任务以及加载的队列
     * @param triggerId 配置id
     */
    void removeTriggerTime(String triggerId,String pipelineId);


    /**
     * 删除
     * @param timeId timeId
     */
    void deleteTriggerTime(String timeId) ;

    /**
     * 更新时间信息
     * @param triggerTime time信息
     */
    void updateTriggerTime(TriggerTime triggerTime);

    /**
     * 查询单个信息
     * @param timeId pipelineTimeId
     * @return time信息
     */
    @FindOne
    TriggerTime findTriggerTime(String timeId) ;

    /**
     * 查询所有信息
     * @return time信息集合
     */
    @FindAll
    List<TriggerTime> findAllTriggerTime() ;

    /**
     * 根据ID列表查询时间
     * @param idList 时间ID列表
     * @return 时间列表
     */
    @FindList
    List<TriggerTime> findTriggerTimeList(List<String> idList);

    /**
     * 根据查询条件查询时间
     * @param query 查询条件
     * @return 时间列表
     */
    List<TriggerTime> findTriggerTimeList(TriggerTimeQuery query);


    /**
     * 分页查询时间
     * @param query 查询条件
     * @return 时间列表
     */
    Pagination<TriggerTime> findTriggerTimePage(TriggerTimeQuery query);



}
