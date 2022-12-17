package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.execute.model.PipelineProcess;

public interface PipelineExecTaskService {


    boolean beginCourseState(PipelineProcess pipelineProcess, String configId ,int taskType);

}
