package com.tiklab.matflow.instance.service.execAchieveService;

import com.tiklab.matflow.instance.model.PipelineExecHistory;
import com.tiklab.matflow.instance.model.PipelineProcess;

import java.util.List;

public interface TestAchieveService {

    /**
     * 测试
     * @param pipelineProcess 配置信息
     * @param pipelineExecHistoryList 执行过程
     * @return 执行状态
     */
    int test(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList);
}
