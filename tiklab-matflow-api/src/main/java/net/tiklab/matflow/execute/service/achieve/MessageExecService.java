package net.tiklab.matflow.execute.service.achieve;

import net.tiklab.matflow.definition.model.PipelineMessageType;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface MessageExecService {


    /**
     * 部署
     * @param pipelineProcess 配置信息
     * @return 状态
     */
    boolean message(PipelineProcess pipelineProcess, PipelineMessageType pipelineMessageType);

}
