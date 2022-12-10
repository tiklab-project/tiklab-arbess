package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.PipelinePost;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface PipelineExecTaskService {


    boolean beginCourseState(PipelineProcess pipelineProcess, String configId ,int taskType);

    boolean beginAfterState(PipelineProcess pipelineProcess, PipelinePost config);

}
