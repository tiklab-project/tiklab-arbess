package net.tiklab.matflow.pipeline.instance.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.pipeline.instance.model.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 流水线历史
 */
@JoinProvider(model = PipelineExecInstance.class)
public interface PipelineExecInstanceService {

    /**
     * 创建流水线历史
     * @param pipelineExecInstance 流水线历史信息
     * @return 流水线id
     */
    String createHistory(@NotNull @Valid PipelineExecInstance pipelineExecInstance);

    /**
     * 创建日志表
     * @param pipelineExecLog 日志信息
     * @return 日志id
     */
    String createLog(PipelineExecLog pipelineExecLog);

    /**
     * 删除流水线历史
     * @param pipelineId 流水线id
     */
    void deleteAllHistory(@NotNull String  pipelineId);

    /**
     * 删除单个历史
     * @param historyId 历史id
     */
    void deleteHistory(@NotNull String  historyId);

    /**
     * 更新流水线历史
     * @param pipelineExecInstance 更新后流水线历史信息
     */
    void updateHistory(@NotNull @Valid PipelineExecInstance pipelineExecInstance);

    /**
     * 查询单个流水线历史
     * @param historyId 流水线历史id
     * @return 流水线历史信息
     */
    @FindOne
    PipelineExecInstance findOneHistory(String historyId);

    /**
     * 查询最近一次历史
     * @param pipelineId 流水线id
     * @return 历史信息
     */
    PipelineExecInstance findLatelyHistory(String pipelineId);


    /**
     * 查询最近一次成功
     * @param pipelineId 流水线id
     * @return 成功信息
     */
    PipelineExecInstance findLatelySuccess(String pipelineId);


    /**
     * 查询所有流水线历史
     * @return 流水线列表历史
     */
    @FindAll
    List<PipelineExecInstance> findAllHistory();

    /**
     * 根据流水线id查询所有历史信息
     * @param pipelineId 流水线id
     * @return 历史信息
     */
    @FindAll
    List<PipelineExecInstance> findAllHistory(String pipelineId);


    /**
     * 所有历史分页
     * @param pipelineHistoryQuery 历史
     * @return 历史
     */
    Pagination<PipelineExecInstance> findUserAllHistory(PipelineAllInstanceQuery pipelineHistoryQuery);


    /**
     * 查询用户所有正在运行的流水线
     * @param pipelineHistoryQuery 分页条件
     * @return 历史
     */
    Pagination<PipelineExecInstance> findUserRunPageHistory(PipelineAllInstanceQuery pipelineHistoryQuery);

    @FindList
    List<PipelineExecInstance> findHistoryList(List<String> idList);


    /**
     * 最近一次的构建历史
     * @param pipelineId 流水线id
     * @return 历史
     */
    PipelineExecInstance findLastHistory(String pipelineId);

    /**
     * 查询流水线正在运行历史
     * @param pipelineId 流水线id
     * @return 运行历史
     */
    PipelineExecInstance findRunHistory(String pipelineId);

    /**
     * 查询日志详情
     * @param historyId 历史id
     * @return 日志集合
     */
    PipelineRunLog findAll(String historyId);


    Map<String,Object> findTimeState(List<PipelineRunLog> logs);

    /**
     * 分页查询历史
     * @param PipelineInstanceQuery 条件
     * @return 历史
     */
    Pagination<PipelineExecInstance> findPageHistory(PipelineInstanceQuery PipelineInstanceQuery);
}
