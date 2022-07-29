package com.tiklab.matfiow.instance.service.execAchieveService;

import com.tiklab.matfiow.instance.model.PipelineExecHistory;
import com.tiklab.matfiow.instance.model.PipelineProcess;

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
