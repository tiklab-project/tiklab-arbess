package net.tiklab.matflow.execute.service;


import net.tiklab.core.page.Pagination;
import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.execute.model.MatFlowExecHistory;
import net.tiklab.matflow.execute.model.MatFlowExecLog;
import net.tiklab.matflow.execute.model.MatFlowHistoryQuery;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 流水线历史
 */
@JoinProvider(model = MatFlowExecHistory.class)
public interface MatFlowExecHistoryService {

    /**
     * 创建流水线历史
     * @param matFlowExecHistory 流水线历史信息
     * @return 流水线id
     */
    String createHistory(@NotNull @Valid MatFlowExecHistory matFlowExecHistory);

    /**
     * 创建日志表
     * @param matFlowExecLog 日志信息
     * @return 日志id
     */
    String createLog(MatFlowExecLog matFlowExecLog);

    /**
     * 删除流水线历史
     * @param historyId 流水线历史id
     */
    void deleteHistory(@NotNull String  historyId);

    /**
     * 更新流水线历史
     * @param matFlowExecHistory 更新后流水线历史信息
     */
    void updateHistory(@NotNull @Valid MatFlowExecHistory matFlowExecHistory);

    /**
     * 查询单个流水线历史
     * @param historyId 流水线历史id
     * @return 流水线历史信息
     */
    @FindOne
    MatFlowExecHistory findOneHistory(String historyId);

    /**
     * 查询最近一次历史
     * @param matFlowId 流水线id
     * @return 历史信息
     */
    MatFlowExecHistory findLatelyHistory(String matFlowId);


    /**
     * 查询用户所有流水线历史
     * @param s 流水线id
     * @param lastTime 开始时间
     * @param nowTime 停止时间
     * @return 历史信息
     */
    List<MatFlowExecHistory> findAllUserHistory(String lastTime, String nowTime, StringBuilder s);


    /**
     * 查询最近一次成功
     * @param matFlowId 流水线id
     * @return 成功信息
     */
    MatFlowExecHistory findLatelySuccess(String matFlowId);


    /**
     * 查询所有流水线历史
     * @return 流水线列表历史
     */
    @FindAll
    List<MatFlowExecHistory> findAllHistory();

    /**
     * 根据流水线id查询所有历史信息
     * @param matFlowId 流水线id
     * @return 历史信息
     */
    @FindAll
    List<MatFlowExecHistory> findAllHistory(String matFlowId);


    @FindList
    List<MatFlowExecHistory> findHistoryList(List<String> idList);

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
    MatFlowExecLog getRunLog(String historyId);


    Pagination<MatFlowExecHistory> findPageHistory(MatFlowHistoryQuery MatFlowHistoryQuery);
}
