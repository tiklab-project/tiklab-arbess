package net.tiklab.matflow.achieve.server;

import net.tiklab.matflow.execute.model.PipelineProcess;

public interface MessageExecService {


    /**
     * 部署
     * @param pipelineProcess 配置信息
     * @return 状态
     */
    boolean message(PipelineProcess pipelineProcess, String configId);

}
