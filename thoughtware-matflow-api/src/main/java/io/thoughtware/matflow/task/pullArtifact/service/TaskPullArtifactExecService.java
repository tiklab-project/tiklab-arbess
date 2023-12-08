package io.thoughtware.matflow.task.pullArtifact.service;

import io.thoughtware.matflow.task.task.model.Tasks;

public interface TaskPullArtifactExecService {


    boolean pullArtifact(String pipelineId, Tasks task , String taskType);


}
