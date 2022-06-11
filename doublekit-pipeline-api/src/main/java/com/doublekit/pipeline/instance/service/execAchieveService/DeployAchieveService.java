package com.doublekit.pipeline.instance.service.execAchieveService;

import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineProcess;

import java.util.List;

public interface DeployAchieveService {

    /**
     * 部署
     * @param pipelineProcess 部署信息
     * @param pipelineExecHistoryList 部署过程
     * @return 部署状态
     */
    int deploy(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList);
}
