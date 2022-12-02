package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.PipelineAfterConfig;
import net.tiklab.matflow.definition.model.PipelineCourseConfig;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface PipelineTaskExecService {


    boolean beginCourseState(PipelineProcess pipelineProcess, PipelineCourseConfig config);

    boolean beginAfterState(PipelineProcess pipelineProcess, PipelineAfterConfig config);

}
