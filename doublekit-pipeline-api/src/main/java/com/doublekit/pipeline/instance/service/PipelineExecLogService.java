package com.doublekit.pipeline.instance.service;

import com.doublekit.join.annotation.FindAll;
import com.doublekit.join.annotation.FindList;
import com.doublekit.join.annotation.FindOne;
import com.doublekit.join.annotation.JoinProvider;
import com.doublekit.pipeline.instance.model.PipelineExecLog;

import java.util.List;
/**
 * 流水线日志
 */
@JoinProvider(model = PipelineExecLog.class)
public interface PipelineExecLogService {

    /**
     * 创建流水线日志
     * @param pipelineExecLog 流水线历史日志
     * @return 流水线日志id
     */
     String createLog(PipelineExecLog pipelineExecLog);

    /**
     * 创建日志以及关联表
     * @return 日志信息
     */
     PipelineExecLog createLog();
    /**
     * 删除流水线日志
     * @param logId 流水线日志id
     */
     void deleteLog(String logId);

    /**
     * 更新流水线日志
     * @param pipelineExecLog 更新后流水线日志信息
     */
     void updateLog(PipelineExecLog pipelineExecLog);

    /**
     * 查询流水线日志
     * @param logId 查询id
     * @return 流水线日志信息
     */
    @FindOne
    PipelineExecLog findOneLog(String logId);

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    @FindAll
     List<PipelineExecLog> findAllLog();

    @FindList
    List<PipelineExecLog> findAllLogList(List<String> idList);
}
