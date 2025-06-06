package io.tiklab.arbess.pipeline.instance.service;


import io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 流水线实例服务接口
 */
@JoinProvider(model = PipelineInstance.class)
public interface PipelineInstanceService {

    /**
     * 创建流水线实例
     * @param pipelineInstance 流水线实例信息
     * @return 流水线id
     */
    String createInstance(@NotNull @Valid PipelineInstance pipelineInstance);

    /**
     * 删除流水线实例
     * @param pipelineId 流水线id
     */
    void deleteAllInstance(@NotNull String pipelineId);

    /**
     * 初始化实例信息
     * @param runMsg 流水线
     * @return 实例
     */
    PipelineInstance initializeInstance(PipelineRunMsg runMsg);


    /**
     * 删除单个实例
     * @param instanceId 实例id
     */
    void deleteInstance(@NotNull String  instanceId);

    /**
     * 更新流水线实例
     * @param pipelineInstance 更新后流水线实例信息
     */
    void updateInstance(@NotNull @Valid PipelineInstance pipelineInstance);

    /**
     * 查询单个流水线实例
     * @param instanceId 流水线实例id
     * @return 流水线实例信息
     */
    @FindOne
    PipelineInstance findOneInstance(String instanceId);

    /**
     * 查询流水线最近一次实例
     * @param pipelineId 流水线id
     * @return 实例信息
     */
    PipelineInstance findLatelyInstance(String pipelineId);

    /**
     * 查询所有流水线实例
     * @return 流水线列表实例
     */
    @FindAll
    List<PipelineInstance> findAllInstance();

    /**
     * 根据流水线id查询所有实例信息
     * @param pipelineId 流水线id
     * @return 实例信息
     */
    @FindAll
    List<PipelineInstance> findPipelineAllInstance(String pipelineId);

    /**
     * 查询用户所有运行实例
     * @param userId 用户id
     * @param limit 查询数量
     * @return 实例
     */
    List<PipelineInstance> findUserPipelineInstance(String userId,Integer limit);


    /**
     * 查询流水线正在运行的实例ID
     * @param pipelineId 流水线id
     * @return 实例id
     */
    String findRunInstanceId(String pipelineId);


    /**
     * 查询流水线实例
     * @param idList 实例id
     * @return 实例
     */
    @FindList
    List<PipelineInstance> findInstanceList(List<String> idList);


    /**
     * 查询流水线实例
     * @param pipelineInstanceQuery 查询条件
     * @return 实例
     */
    List<PipelineInstance> findPipelineInstanceList(PipelineInstanceQuery pipelineInstanceQuery);


    /**
     * 查询用户所有实例分页
     * @param pipelineInstanceQuery 实例
     * @return 实例
     */
    Pagination<PipelineInstance> findUserInstance(PipelineInstanceQuery pipelineInstanceQuery);


    /**
     * 查询单个流水线历史
     * @param query 条件
     * @return 实例
     */
    Pagination<PipelineInstance> findPipelineInstance(PipelineInstanceQuery query);


    /**
     * 查询流水线实例
     * @param pipelineId 流水线id
     * @param queryTime 查询时间 [开始时间,结束时间]
     * @return 实例
     */
    List<PipelineInstance> findInstanceByTime(String pipelineId,String[] queryTime);


    /**
     * 查询流水线实例
     * @param queryTime 查询时间 [开始时间,结束时间]
     * @return 实例
     */
    List<PipelineInstance> findInstanceByTime(String[] queryTime,String[] pipelineIdList);

    /**
     * 获取实例运行时间
     * @param instanceId  实例id
     * @return 运行时间
     */
    int findInstanceRuntime(String instanceId);

    /**
     * 更新实例运行时间
     * @param instanceId 实例id
     */
    void instanceRuntime(String instanceId);


    /**
     * 停止线程执行
     * @param threadName 线程名称
     */
    void stopThread(String threadName);

}






















