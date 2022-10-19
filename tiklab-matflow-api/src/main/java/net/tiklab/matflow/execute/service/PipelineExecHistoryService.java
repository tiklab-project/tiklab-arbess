package net.tiklab.matflow.execute.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineHistoryQuery;

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
     * 创建日志表
     * @param pipelineExecLog 日志信息
     * @return 日志id
     */
    String createLog(PipelineExecLog pipelineExecLog);

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
    PipelineExecHistory findOneHistory(String historyId);

    /**
     * 查询最近一次历史
     * @param pipelineId 流水线id
     * @return 历史信息
     */
    PipelineExecHistory findLatelyHistory(String pipelineId);


    /**
     * 查询用户所有流水线历史
     * @param s 流水线id
     * @param lastTime 开始时间
     * @param nowTime 停止时间
     * @return 历史信息
     */
    List<PipelineExecHistory> findAllUserHistory(String lastTime, String nowTime, StringBuilder s);


    /**
     * 查询最近一次成功
     * @param pipelineId 流水线id
     * @return 成功信息
     */
    PipelineExecHistory findLatelySuccess(String pipelineId);


    /**
     * 查询所有流水线历史
     * @return 流水线列表历史
     */
    @FindAll
    List<PipelineExecHistory> findAllHistory();

    /**
     * 根据流水线id查询所有历史信息
     * @param pipelineId 流水线id
     * @return 历史信息
     */
    @FindAll
    List<PipelineExecHistory> findAllHistory(String pipelineId);


    @FindList
    List<PipelineExecHistory> findHistoryList(List<String> idList);

    /**
     * 时间转换器
     * @param time 时间
     * @return 时间
     */
    String formatDateTime(long time);

    /**
     * 获取最后的执行日志
     * @param historyId 历史id
     * @return 执行日志
     */
    PipelineExecLog getRunLog(String historyId);


    /**
     * 分页查询历史
     * @param PipelineHistoryQuery 条件
     * @return 历史
     */
    Pagination<PipelineExecHistory> findPageHistory(PipelineHistoryQuery PipelineHistoryQuery);
}
