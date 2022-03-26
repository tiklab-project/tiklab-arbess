package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 流水线历史
 */
@JoinProvider(model = PipelineExecHistory.class)
public interface PipelineExecHistoryService {

    /**
     * 创建流水线历史
     * @param pipelineExecHistory 流水线历史信息
     * @return 流水线id
     */
    String createHistory(@NotNull @Valid PipelineExecHistory pipelineExecHistory);

    /**
     * 删除流水线历史
     * @param historyId 流水线历史id
     */
    void deleteHistory(@NotNull String  historyId);

    /**
     * 更新流水线历史
     * @param pipelineExecHistory 更新后流水线历史信息
     */
    void updateHistory(@NotNull @Valid PipelineExecHistory pipelineExecHistory);

    /**
     * 查询单个流水线历史
     * @param historyId 流水线历史id
     * @return 流水线历史信息
     */
    @FindOne
    PipelineExecHistory findOneHistory(@NotNull String historyId);

    /**
     * 查询所有流水线历史
     * @return 流水线列表历史
     */
    @FindAll
    List<PipelineExecHistory> findAllHistory();

    @FindList
    List<PipelineExecHistory> findHistoryList(List<String> idList);


}
