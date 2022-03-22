package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.instance.model.History;
import com.doublekit.pipeline.instance.model.HistoryDetails;
import com.doublekit.pipeline.instance.model.Log;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 流水线历史
 */
@JoinProvider(model = History.class)
public interface HistoryService {

    /**
     * 创建流水线历史
     * @param history 流水线历史信息
     * @return 流水线id
     */
    String createHistory(@NotNull @Valid History history);

    /**
     * 删除流水线历史
     * @param historyId 流水线历史id
     */
    void deleteHistory(@NotNull String  historyId);

    /**
     * 根据流水线id删除全部历史
     * @param pipelineId 流水线id
     */
    void deleteAllHistory(@NotNull String  pipelineId);

    /**
     * 更新流水线历史
     * @param history 更新后流水线历史信息
     */
    void updateHistory(@NotNull @Valid History history);

    /**
     * 查询单个流水线历史
     * @param historyId 流水线历史id
     * @return 流水线历史信息
     */
    @FindOne
    History findOneHistory(@NotNull String historyId);

    /**
     * 查询所有流水线历史
     * @return 流水线列表历史
     */
    @FindAll
    List<History> findAllHistory();

    /**
     * 获取最近一次的构建
     * @return 构建历史
     */
    @FindOne
    History findLastPipelineHistory(String pipelineId);

    /**
     * 根据流水线id查询所有构建信息
     * @param pipelineId 流水线id
     * @return 配置信息集合
     */
    List<HistoryDetails> findAll(String pipelineId);

    /**
     * 根据流水线id获取历史记录
     * @param pipelineId 流水线id
     * @return 历史记录集合
     */
    List<History> findAllPipelineIdList(String pipelineId);

    /**
     * 完善历史信息
     * @param history 历史信息
     */
    void perfectHistory(History history);

    /**
     * 根据历史查询日志信息
     * @param historyId 历史id
     * @return 日志信息
     */
    Log findHistoryLog(String historyId);

    /**
     * 删除历史以及日志
     * @param HistoryId 历史id
     */
    void deleteHistoryLog(String HistoryId);

    @FindList
    List<History> findHistoryList(List<String> idList);


}
