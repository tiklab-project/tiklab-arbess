package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.instance.model.PipelineHistoryDetails;
import com.doublekit.pipeline.instance.model.PipelineLog;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@JoinProvider(model = PipelineHistory.class)
public interface PipelineHistoryService {

    /**
     * 创建流水线历史
     * @param pipelineHistory 流水线历史信息
     * @return 流水线id
     */
    String createPipelineHistory(@NotNull @Valid PipelineHistory pipelineHistory);

    /**
     * 删除流水线历史
     * @param historyId 流水线历史id
     */
    void deletePipelineHistory(@NotNull String  historyId);

    /**
     * 根据流水线id删除全部历史
     * @param pipelineId 流水线id
     */
    void deleteAllPipelineHistory(@NotNull String  pipelineId);

    /**
     * 更新流水线历史
     * @param pipelineHistory 更新后流水线历史信息
     */
    void updatePipelineHistory(@NotNull @Valid PipelineHistory pipelineHistory);

    /**
     * 查询单个流水线历史
     * @param historyId 流水线历史id
     * @return 流水线历史信息
     */
    @FindOne
    PipelineHistory selectPipelineHistory(@NotNull String historyId);

    /**
     * 查询所有流水线历史
     * @return 流水线列表历史
     */
    @FindAll
    List<PipelineHistory> selectAllPipelineHistory();

    /**
     * 获取最近一次的构建
     * @return 构建历史
     */
    @FindOne
    PipelineHistory selectLastPipelineHistory(String pipelineId);

    /**
     * 根据流水线id查询所有构建信息
     * @param pipelineId 流水线id
     * @return 配置信息集合
     */
    List<PipelineHistoryDetails> selectAll(String pipelineId);

    /**
     * 根据流水线id获取历史记录
     * @param pipelineId 流水线id
     * @return 历史记录集合
     */
    List<PipelineHistory> selectAllPipelineIdList(String pipelineId);

    /**
     * 完善历史信息
     * @param pipelineHistory 历史信息
     */
    void foundPipelineHistory(PipelineHistory pipelineHistory);

    /**
     * 根据历史查询日志信息
     * @param historyId 历史id
     * @return 日志信息
     */
    PipelineLog selectHistoryLog(String historyId);

    /**
     * 删除历史以及日志
     * @param HistoryId 历史id
     */
    void deleteHistoryLog(String HistoryId);

    @FindList
    List<PipelineHistory> selectPipelineHistoryList(List<String> idList);


}
