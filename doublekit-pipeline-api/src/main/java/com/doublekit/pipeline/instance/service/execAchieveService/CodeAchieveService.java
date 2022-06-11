package com.doublekit.pipeline.instance.service.execAchieveService;

import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineProcess;

import java.util.List;

public interface CodeAchieveService {

    /**
     * 源码管理
     * @param pipelineProcess 执行信息
     * @param pipelineExecHistoryList 执行过程
     * @return 执行状态
     */
    int clone(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList);
}
