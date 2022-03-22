package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.instance.model.Log;

import java.util.List;
/**
 * 流水线日志
 */
@JoinProvider(model = Log.class)
public interface LogService {

    /**
     * 创建流水线日志
     * @param log 流水线历史日志
     * @return 流水线日志id
     */
     String createLog(Log log);

    /**
     * 删除流水线日志
     * @param id 流水线日志id
     */
     void deleteLog(String id);

    /**
     * 更新流水线日志
     * @param log 更新后流水线日志信息
     */
     void updateLog(Log log);

    /**
     * 查询流水线日志
     * @param logId 查询id
     * @return 流水线日志信息
     */
    @FindOne
    Log findOneLog(String logId);

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    @FindAll
     List<Log> findAllLog();

    void addHistoryThree(String pipelineId ,String logId );

     //创建历史表
    String createHistory(String logId);

    @FindList
    List<Log> findAllLogList(List<String> idList);
}
