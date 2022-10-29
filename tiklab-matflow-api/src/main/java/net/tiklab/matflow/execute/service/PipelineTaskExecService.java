package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.PipelineConfigOrder;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface PipelineTaskExecService {


    boolean beginState(PipelineProcess pipelineProcess, PipelineConfigOrder oneConfig, int type);

}
