package com.tiklab.matflow.instance.service.execAchieveService;

import com.tiklab.matflow.instance.model.PipelineExecHistory;
import com.tiklab.matflow.instance.model.PipelineProcess;

import java.util.List;

public interface StructureAchieveService {

    /**
     * 构建
     * @param pipelineProcess 构建信息
     * @param pipelineExecHistoryList 构建过程
     * @return 构建状态
     */
    int structure(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList);
}
