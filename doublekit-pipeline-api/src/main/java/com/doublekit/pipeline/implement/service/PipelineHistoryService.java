package com.doublekit.pipeline.implement.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.implement.model.PipelineHistory;
import com.doublekit.pipeline.implement.model.PipelineHistoryDetails;

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
     * @param id 流水线历史id
     */
    void deletePipelineHistory(@NotNull String  id);

    /**
     * 更新流水线历史
     * @param pipelineHistory 更新后流水线历史信息
     */
    void updatePipelineHistory(@NotNull @Valid PipelineHistory pipelineHistory);

    /**
     * 查询单个流水线历史
     * @param id 流水线历史id
     * @return 流水线历史信息
     */
    @FindOne
    PipelineHistory selectPipelineHistory(@NotNull String id);

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

    @FindList
    List<PipelineHistory> selectPipelineHistoryList(List<String> idList);



}
