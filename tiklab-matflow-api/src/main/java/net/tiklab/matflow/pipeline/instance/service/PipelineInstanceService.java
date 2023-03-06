package net.tiklab.matflow.pipeline.instance.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;

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
     * @param pipelineId 流水线id
     * @param startWAy 运行方式
     * @return 实例
     */
    PipelineInstance initializeInstance(String pipelineId , int startWAy);


    /**
     * 获取流水线正在执行的实例
     * @param pipelineId 流水线id
     * @return 实例
     */
    PipelineInstance findPipelineExecInstance(String pipelineId);

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


    @FindList
    List<PipelineInstance> findInstanceList(List<String> idList);


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



}






















