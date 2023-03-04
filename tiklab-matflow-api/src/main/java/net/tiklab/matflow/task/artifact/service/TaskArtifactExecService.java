package net.tiklab.matflow.task.artifact.service;

import net.tiklab.matflow.task.task.model.Tasks;

public interface TaskArtifactExecService {

    /**
     * 推送制品代码
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
     boolean product(String pipelineId, Tasks task , int taskType);


}
