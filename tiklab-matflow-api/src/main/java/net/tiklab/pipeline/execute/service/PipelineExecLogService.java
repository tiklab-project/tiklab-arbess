package net.tiklab.pipeline.execute.service;


import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.pipeline.execute.model.PipelineExecLog;

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
     * @param pipelineExecLog 信息
     */
    void updateLog(PipelineExecLog pipelineExecLog);
    /**
     * 查询日志信息
     * @param historyId 历史id
     * @return 信息集合
     */
    List<PipelineExecLog> findAllLog(String historyId);

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    @FindAll
     List<PipelineExecLog> findAllLog();


    @FindList
    List<PipelineExecLog> findAllLogList(List<String> idList);
}
