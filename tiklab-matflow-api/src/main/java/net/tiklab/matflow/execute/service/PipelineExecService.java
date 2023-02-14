package net.tiklab.matflow.execute.service;

import net.tiklab.core.page.Pagination;
import net.tiklab.matflow.execute.model.PipelineAllHistoryQuery;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineRunLog;

/**
 * 流水线构建
 */
public interface PipelineExecService {

    /**
     * 开始构建
     * @param pipelineId 流水线id
     * @return 构建开始
     */
    boolean  start(String pipelineId,int startWAy);


    /**
     * 查询流水线运行状态
     * @param pipelineId 流水线id
     * @return 运行状态
     */
    PipelineRunLog findPipelineRunMessage(String pipelineId);

    /**
     * 判断运行状态
     * @param pipelineId 流水线ID
     * @return 状态
     */
    int findPipelineState(String pipelineId);

    /**
     * 关闭运行
     * @param pipelineId 流水线id
     */
    void killInstance(String pipelineId);


    /**
     * 获取正在运行的流水线
     * @param query 分页
     * @return 流水线信息
     */
    Pagination<PipelineExecHistory> findUserRunPageHistory(PipelineAllHistoryQuery query);



}


































