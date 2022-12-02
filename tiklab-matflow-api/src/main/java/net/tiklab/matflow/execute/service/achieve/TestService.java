package net.tiklab.matflow.execute.service.achieve;

import net.tiklab.matflow.definition.model.PipelineCourseConfig;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface TestService {

    /**
     * 测试
     * @param pipelineProcess 配置信息
     * @return 执行状态
     */
    boolean test(PipelineProcess pipelineProcess, PipelineCourseConfig config);
}
