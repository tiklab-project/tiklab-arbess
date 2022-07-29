package com.tiklab.matflow.instance.service;


import com.tiklab.join.annotation.FindAll;
import com.tiklab.join.annotation.FindList;
import com.tiklab.join.annotation.JoinProvider;
import com.tiklab.matflow.instance.model.MatFlowExecLog;

import java.util.List;
/**
 * 流水线日志
 */
@JoinProvider(model = MatFlowExecLog.class)
public interface MatFlowExecLogService {

    /**
     * 创建流水线日志
     * @param matFlowExecLog 流水线历史日志
     * @return 流水线日志id
     */
     String createLog(MatFlowExecLog matFlowExecLog);

    /**
     * 删除流水线日志
     * @param logId 流水线日志id
     */
     void deleteLog(String logId);

    /**
     * 根据历史删除日志
     * @param historyId 历史id
     */
    void deleteHistoryLog(String historyId);

    /**
     * 更新日志信息
     * @param matFlowExecLog 信息
     */
    void updateLog(MatFlowExecLog matFlowExecLog);
    /**
     * 查询日志信息
     * @param historyId 历史id
     * @return 信息集合
     */
    List<MatFlowExecLog> findAllLog(String historyId);

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    @FindAll
     List<MatFlowExecLog> findAllLog();


    @FindList
    List<MatFlowExecLog> findAllLogList(List<String> idList);
}
