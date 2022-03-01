package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.instance.model.PipelineLog;


public interface GitCloneService {

    /**
     * 查询构建状态
     * @return 状态信息
     * @throws InterruptedException 延时异常
     */
    PipelineLog selectStructureState() throws InterruptedException;

    /**
     * 开始构建
     * @param pipelineId 流水线id
     * @return 收到构建命令
     * @throws Exception 执行构建异常
     */
    String pipelineStructure(String pipelineId) throws Exception;

    /**
     * 完善历史表
     * @param pipelineId  流水线id
     * @return 历史表
     */
    PipelineHistory pipelineHistoryTwo(String pipelineId);

}
