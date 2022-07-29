package com.tiklab.matflow.instance.service.execAchieveService;

import com.tiklab.matflow.instance.model.PipelineExecHistory;
import com.tiklab.matflow.instance.model.PipelineProcess;

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
