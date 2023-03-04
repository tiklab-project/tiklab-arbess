package net.tiklab.matflow.task.build.service;

import net.tiklab.matflow.task.task.model.Tasks;

public interface TaskBuildExecService {

    /**
     * 构建
     * @param pipelineProcess 构建信息
     * @return 构建状态
     */
    boolean build(String pipelineId, Tasks task , int taskType);


}
