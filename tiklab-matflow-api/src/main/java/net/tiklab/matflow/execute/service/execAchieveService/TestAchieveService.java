package net.tiklab.matflow.execute.service.execAchieveService;

import net.tiklab.matflow.definition.model.PipelineTest;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface TestAchieveService {

    /**
     * 测试
     * @param pipelineProcess 配置信息
     * @return 执行状态
     */
    String test(PipelineProcess pipelineProcess, PipelineTest pipelineTest);
}
