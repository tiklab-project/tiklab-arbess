package com.tiklab.matfiow.instance.service.execAchieveService;

import com.tiklab.matfiow.instance.model.PipelineExecHistory;
import com.tiklab.matfiow.instance.model.PipelineProcess;

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
