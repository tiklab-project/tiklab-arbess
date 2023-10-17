package io.tiklab.matflow.task.pullArtifact.service;

import io.tiklab.matflow.task.task.model.Tasks;

public interface TaskPullArtifactExecService {


    boolean pullArtifact(String pipelineId, Tasks task , String taskType);


}
